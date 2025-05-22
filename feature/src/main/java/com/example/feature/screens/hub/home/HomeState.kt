package com.example.feature.screens.hub.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post

@Immutable
data class HomeState(
    val posts: List<Post> = listOf(),
    val haikus: List<Post> = listOf(),
    val tankas: List<Post> = listOf(),
    val katautas: List<Post> = listOf(),
    val sedoukas: List<Post> = listOf(),
    val currentGenre: Genre = Genre.NORMAL,
    val isAllPostsFetched: Boolean = false,
    val isAllHaikusFetched: Boolean = false,
    val isAllTankasFetched: Boolean = false,
    val isAllKatautasFetched: Boolean = false,
    val isAllSedoukasFetched: Boolean = false,
    val lazyListStateOfPost: LazyListState = LazyListState(),
    val lazyListStateOfHaiku: LazyListState = LazyListState(),
    val lazyListStateOfTanka: LazyListState = LazyListState(),
    val lazyListStateOfKatauta: LazyListState = LazyListState(),
    val lazyListStateOfSedouka: LazyListState = LazyListState(),
)
