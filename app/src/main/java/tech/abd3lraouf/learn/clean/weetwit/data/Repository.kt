package tech.abd3lraouf.learn.clean.weetwit.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import tech.abd3lraouf.learn.clean.weetwit.BuildConfig
import tech.abd3lraouf.learn.clean.weetwit.data.api.TwitterService
import tech.abd3lraouf.learn.clean.weetwit.data.mapper.DataResponseMapper
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.ResponseEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.port.IRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val twitterService: TwitterService,
    private val mapper:DataResponseMapper
) : IRepository {

    override suspend fun getSearchResults(query: String, resultType: String?): Flow<ResponseEntity> {
        return flow {
            val result = twitterService.searchTweets(BuildConfig.TOKEN, query, resultType)
            emit(mapper.mapToDomain(result))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNextResults(nextUrl: String): Flow<ResponseEntity> {
        return flow {
            val result = twitterService.getResultsForUrl(BuildConfig.TOKEN, nextUrl)
            emit(mapper.mapToDomain(result))
        }.flowOn(Dispatchers.IO)
    }
}