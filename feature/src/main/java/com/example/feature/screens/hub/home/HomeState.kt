package com.example.feature.screens.hub.home

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post

@Immutable
data class HomeState(
    val posts: List<Post> = listOf(),
    val currentGenre: Genre = Genre.NORMAL,
    val hasNoMorePost: Boolean = false,
)
