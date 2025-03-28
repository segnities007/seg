package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

@Immutable
sealed class AccountAction{
    data object ResetState: AccountAction()
    data object GetPosts: AccountAction()
    data object ToggleIsLoading: AccountAction()
    data class InitAccountState(val userID: String): AccountAction()
    data class GetOtherUser(val userID: String): AccountAction()
    data class SetOtherUser(val user: User): AccountAction()
    data class GetUserPosts(val userID: String): AccountAction()
    data class ClickFollowButton(
        val isFollow: Boolean,
        val self: User,
        val other: User,
        val getSelf: () -> Unit,
    ): AccountAction()
    data class ProcessOfEngagementAction(val newPost: Post): AccountAction()
}