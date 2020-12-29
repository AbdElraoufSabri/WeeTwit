package tech.abd3lraouf.learn.clean.weetwit.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import tech.abd3lraouf.learn.clean.weetwit.BuildConfig
import tech.abd3lraouf.learn.clean.weetwit.data.Repository
import tech.abd3lraouf.learn.clean.weetwit.data.model.ResponseModel
import tech.abd3lraouf.learn.clean.weetwit.data.model.StatusModel
import tech.abd3lraouf.learn.clean.weetwit.util.defaultObserverAndSubscribe

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private var searchDisposable: Disposable? = null
    private var currentQuery: String? = null

    val error: MutableLiveData<String> = MutableLiveData();
    val searchResults: MutableLiveData<ResponseModel> = MutableLiveData();

    fun search(query: String) {
        currentQuery = query

        dispose()

        if (query.isEmpty()) {
            searchResults.postValue(null)
            return
        }

        searchDisposable = repository.getSearchResults(query, null)
            .defaultObserverAndSubscribe()
            ?.subscribe(
                { results -> searchResults.postValue(results) },
                { err -> error.postValue(err.message) })
    }

    fun next() {
        dispose()

        var nextUrl = searchResults.value?.metadataModel?.nextResults
        if (nextUrl == null) {
            error.postValue("No more tweets to fetch.")
            return
        }

        nextUrl = BuildConfig.BASE_URL + "search/tweets.json" + nextUrl

        searchDisposable = repository.getNextResults(nextUrl)
            .defaultObserverAndSubscribe()
            ?.subscribe(
                { results ->
                    processNextResults(searchResults.value?.statusList, results.statusList)?.let { results.statusList = it }
                    searchResults.postValue(results)
                },
                { err -> error.postValue(err.message) })
    }

    fun refresh() {
        currentQuery?.let { search(it) } ?: error.postValue("No search to refresh.")

    }

    private fun processNextResults(oldTweets: List<StatusModel>?, newTweets: List<StatusModel>): List<StatusModel>? {
        val mergedTweets = oldTweets?.toMutableList()
        newTweets.let { tweets -> mergedTweets?.addAll(tweets) }
        return mergedTweets
    }

    private fun dispose() {
        searchDisposable?.dispose()
        searchDisposable = null
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}