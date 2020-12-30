package tech.abd3lraouf.learn.clean.weetwit.presentation.features

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import tech.abd3lraouf.learn.clean.weetwit.BuildConfig
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.state.TweetUiState
import tech.abd3lraouf.learn.clean.weetwit.domain.features.GetTweetsUseCase

class HomeViewModel @ViewModelInject constructor(
    private val tweetsUseCase: GetTweetsUseCase
) : ViewModel() {

    private var currentQuery: String? = null
    val searchResults = tweetsUseCase.stateFlow.asLiveData() as MutableLiveData

    suspend fun search(query: String) {
        currentQuery = query
        if (query.isEmpty()) {
            searchResults.postValue(TweetUiState.EmptyUiState)
            return
        }

        tweetsUseCase.execute(params = Pair(query, null))
    }

    suspend fun next() {

        val nextUrl = when (val value = searchResults.value) {
            is TweetUiState.SuccessUiState -> value.responseEntity.metadataEntity.nextResults
            else -> null
        }

        if (nextUrl == null) {
            searchResults.postValue(TweetUiState.ErrorUiState("No more tweets"))
            return
        }
        val params = BuildConfig.BASE_URL + "search/tweets.json" + nextUrl
        tweetsUseCase.execute(params = Pair(null, params))
    }


    suspend fun refresh() {
        currentQuery?.let { search(it) }
        if (currentQuery==null) searchResults.postValue(TweetUiState.ErrorUiState("Search is empty"))
    }

}