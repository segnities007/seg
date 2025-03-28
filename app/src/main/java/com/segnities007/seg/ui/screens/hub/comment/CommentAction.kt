package com.segnities007.seg.ui.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post

@Immutable
sealed class CommentAction{
    data class GetComments(val comment: Post): CommentAction()
    data class ProcessOfEngagementAction(val updatedPost: Post): CommentAction()
}