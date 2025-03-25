package com.segnities007.seg.ui.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post

@Immutable
data class CommentState(
    val comments: List<Post> = listOf(),
)
