package tech.abd3lraouf.learn.clean.weetwit.domain.port

import io.reactivex.Single
import tech.abd3lraouf.learn.clean.weetwit.data.model.ResponseModel

interface IRepository {
    fun getSearchResults(query: String, resultType: String?): Single<ResponseModel>
    fun getNextResults(nextUrl: String): Single<ResponseModel>
}