package tech.abd3lraouf.learn.clean.weetwit.data

import io.reactivex.Single
import tech.abd3lraouf.learn.clean.weetwit.BuildConfig
import tech.abd3lraouf.learn.clean.weetwit.data.api.TwitterService
import tech.abd3lraouf.learn.clean.weetwit.data.model.ResponseModel
import tech.abd3lraouf.learn.clean.weetwit.domain.port.IRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val twitterService: TwitterService
) : IRepository {

    override fun getSearchResults(query: String, resultType: String?): Single<ResponseModel> {
        return twitterService.searchTweets(BuildConfig.TOKEN, query, resultType);
    }

    override fun getNextResults(nextUrl: String): Single<ResponseModel> {
        return twitterService.getResultsForUrl(BuildConfig.TOKEN, nextUrl);
    }
}