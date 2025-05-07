package com.example.feature.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post

@Immutable
data class CommentState(
    val comments: List<Post> = listOf(),
)
