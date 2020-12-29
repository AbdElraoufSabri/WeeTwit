package tech.abd3lraouf.learn.clean.weetwit.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url
import tech.abd3lraouf.learn.clean.weetwit.data.model.ResponseModel

interface TwitterService {
    @GET("search/tweets.json")
    fun searchTweets(
        @Header("Authorization") authHeader: String,
        @Query("q") searchQuery: String,
        @Query("result_type") resultType: String?
    ): Single<ResponseModel>

    @GET
    fun getResultsForUrl(
        @Header("Authorization") authHeader: String,
        @Url urlString: String
    ): Single<ResponseModel>
}