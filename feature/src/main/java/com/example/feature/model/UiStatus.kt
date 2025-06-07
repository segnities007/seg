package com.example.feature.model

sealed interface UiStatus {
    object Initial : UiStatus

    object Loading : UiStatus

    object Success : UiStatus

    data class Error(
        val message: String,
    ) : UiStatus
}
