package com.example.feature.screens.hub.account

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction

@Immutable
sealed interface AccountAction {
    data object ResetState : AccountAction

    data object GetPosts : AccountAction

    data class InitAccountState(
        val userID: String,
    ) : AccountAction

    data class GetOtherUser(
        val userID: String,
    ) : AccountAction

    data class SetOtherUser(
        val user: User,
    ) : AccountAction

    data class GetUserPosts(
        val userID: String,
    ) : AccountAction

    data class ClickFollowButton(
        val isFollow: Boolean,
        val self: User,
        val other: User,
        val onHubAction: (HubAction) -> Unit,
    ) : AccountAction

    data class ProcessOfEngagementAction(
        val newPost: Post,
    ) : AccountAction

    data class UpdateUiStatus(
        val uiStatus: UiStatus,
    ) : AccountAction
}
