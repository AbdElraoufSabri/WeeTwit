package tech.abd3lraouf.learn.clean.weetwit.domain.port

import kotlinx.coroutines.flow.Flow
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.ResponseEntity

interface IRepository {
    suspend fun getSearchResults(query: String, resultType: String?): Flow<ResponseEntity>
    suspend fun getNextResults(nextUrl: String): Flow<ResponseEntity>
}