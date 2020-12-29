package tech.abd3lraouf.learn.clean.weetwit.domain.core

abstract class FlowableUseCase<out T, in PARAMS>  {

    abstract fun cancel()
    abstract suspend fun execute(params: PARAMS?=null)
}