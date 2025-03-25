package com.segnities007.seg.ui.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post

@Immutable
data class CommentAction(
    val onGetComments: (comment: Post) -> Unit,
    val onProcessOfEngagementAction: (updatedPost: Post) -> Unit,
)
