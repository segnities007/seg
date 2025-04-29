package com.example.feature.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.example.domain.model.Post

@Immutable
sealed class CommentAction {
    data class GetComments(
        val comment: Post,
    ) : CommentAction()

    data class ProcessOfEngagementAction(
        val updatedPost: Post,
    ) : CommentAction()
}
