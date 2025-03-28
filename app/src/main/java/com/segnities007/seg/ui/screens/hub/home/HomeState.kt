package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post

@Immutable
data class HomeState(
    val posts: List<Post> = listOf(),
    val hasNoMorePost: Boolean = false,
)