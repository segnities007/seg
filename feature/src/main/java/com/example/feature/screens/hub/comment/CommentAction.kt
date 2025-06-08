package com.example.feature.screens.hub.comment

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.home.HomeAction

@Immutable
sealed interface CommentAction : HomeAction {
    data class GetComments(
        val comment: Post,
        val onHubAction: (HubAction) -> Unit,
    ) : CommentAction

    data class ProcessOfEngagementAction(
        val updatedPost: Post,
    ) : CommentAction
}
