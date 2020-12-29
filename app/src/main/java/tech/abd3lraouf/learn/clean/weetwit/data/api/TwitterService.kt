package tech.abd3lraouf.learn.clean.weetwit.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url
import tech.abd3lraouf.learn.clean.weetwit.data.model.ResponseModel

interface TwitterService {
    @GET("search/tweets.json")
    suspend fun searchTweets(
        @Header("Authorization") authHeader: String,
        @Query("q") searchQuery: String,
        @Query("result_type") resultType: String?
    ): ResponseModel

    @GET
    suspend fun getResultsForUrl(
        @Header("Authorization") authHeader: String,
        @Url urlString: String
    ): ResponseModel
}