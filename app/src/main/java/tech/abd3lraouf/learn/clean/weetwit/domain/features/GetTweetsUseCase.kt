package tech.abd3lraouf.learn.clean.weetwit.domain.features

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import tech.abd3lraouf.learn.clean.weetwit.domain.core.FlowableUseCase
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.ResponseEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.StatusEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.state.TweetUiState
import tech.abd3lraouf.learn.clean.weetwit.domain.port.IRepository

class GetTweetsUseCase constructor(
    private val scope: CoroutineScope,
    private val repository: IRepository
) : FlowableUseCase<ResponseEntity, Pair<String?, String?>>() {
    var next = false
    private val _stateFlow = MutableSharedFlow<TweetUiState>(2)

    val stateFlow: SharedFlow<TweetUiState> get() = _stateFlow

    override suspend fun execute(params: Pair<String?, String?>?) {
        _stateFlow.tryEmit(TweetUiState.LoadingUiState)

        val split = if (params?.second == null) {
            next = false
            repository.getSearchResults(params?.first!!, null)
        } else {
            next = true
            repository.getNextResults(params.second!!)
        }

        split.onEach { entity ->

            val stateFlow = if (entity.statusList.isEmpty())
                TweetUiState.EmptyUiState
            else {
                if (next) {
                    val old: TweetUiState.SuccessUiState? = stateFlow.replayCache.findLast { it is TweetUiState.SuccessUiState } as? TweetUiState.SuccessUiState
                    processNextResults(old?.responseEntity?.statusList, entity.statusList)?.let { entity.statusList = it }
                }
                TweetUiState.SuccessUiState(entity)
            }
            _stateFlow.tryEmit(stateFlow)
        }.catch { e ->
            _stateFlow.tryEmit(TweetUiState.ErrorUiState(e.localizedMessage!!))
        }.launchIn(scope)

    }

    override fun cancel() = scope.cancel()

    private fun processNextResults(oldTweets: List<StatusEntity>?, newTweets: List<StatusEntity>): List<StatusEntity>? {
        val mergedTweets = oldTweets?.toMutableList()
        mergedTweets?.addAll(newTweets)
        return mergedTweets
    }

}