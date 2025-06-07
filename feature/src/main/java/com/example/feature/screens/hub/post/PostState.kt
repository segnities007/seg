package com.example.feature.screens.hub.post

import com.example.domain.model.post.Genre
import com.example.feature.model.UiStatus

data class PostState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val inputText: String = "",
    val isLoading: Boolean = false,
    val genre: Genre = Genre.NORMAL,
)
