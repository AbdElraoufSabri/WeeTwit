package tech.abd3lraouf.learn.clean.weetwit.domain.features

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.abd3lraouf.learn.clean.weetwit.domain.core.FlowableUseCase
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.ResponseEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.port.IRepository
import javax.inject.Inject

class GetNextTweetsUseCase @Inject constructor(
    private val scope: CoroutineScope,
    private val repository: IRepository
) : FlowableUseCase<ResponseEntity, String>() {

    private val _stateFlow = MutableStateFlow<TweetUiState>(TweetUiState.UnInitialized)

    val stateFlow get() = _stateFlow

    override suspend fun execute(params: String?) {
        _stateFlow.value = TweetUiState.LoadingUiState

        repository.getNextResults(params!!)
            .onEach {
                val stateFlow = if (it.statusList.isEmpty()) TweetUiState.EmptyUiState else TweetUiState.SuccessUiState(it)
                _stateFlow.value = stateFlow
            }.catch { e -> _stateFlow.value = TweetUiState.ErrorUiState(e.localizedMessage!!) }
            .launchIn(scope)
    }

    override fun cancel() = scope.cancel()
}