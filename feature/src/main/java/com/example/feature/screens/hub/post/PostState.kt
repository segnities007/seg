package com.example.feature.screens.hub.post

import com.example.domain.model.post.Genre

data class PostState(
    val inputText: String = "",
    val isLoading: Boolean = false,
    val genre: Genre = Genre.NORMAL,
)
