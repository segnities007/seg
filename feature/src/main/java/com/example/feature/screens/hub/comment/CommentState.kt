package com.example.feature.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import com.example.feature.model.UiStatus

@Immutable
data class CommentState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val comments: List<Post> = listOf(),
)
