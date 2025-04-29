package com.example.feature.screens.hub.home

import androidx.compose.runtime.Immutable
import com.example.domain.model.Post

@Immutable
data class HomeState(
    val posts: List<Post> = listOf(),
    val hasNoMorePost: Boolean = false,
)
