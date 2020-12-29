package tech.abd3lraouf.learn.clean.weetwit.domain.features

import tech.abd3lraouf.learn.clean.weetwit.domain.entity.ResponseEntity

sealed class TweetUiState {
    object UnInitialized : TweetUiState()
    object EmptyUiState : TweetUiState()
    object LoadingUiState : TweetUiState()
    data class ErrorUiState(val message: String) : TweetUiState()
    data class SuccessUiState(val responseEntity: ResponseEntity) : TweetUiState()
}