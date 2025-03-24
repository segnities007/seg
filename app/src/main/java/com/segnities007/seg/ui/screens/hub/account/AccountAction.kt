package com.segnities007.seg.ui.screens.hub.account

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

data class AccountAction(
    val onReset: () -> Unit,
    val onInitAccountUiState: (userID: String) -> Unit,
    val onGetOtherUser: (userID: String) -> Unit,
    val onSetOtherUser: (user: User) -> Unit,
    val onGetUserPosts: (userID: String) -> Unit,
    val onToggleIsLoading: () -> Unit,
    val onToggleIsCompletedFetchPosts: () -> Unit,
    val onGetPosts: () -> Unit,
    val onFollow: (
        isFollow: Boolean,
        myself: User,
        other: User,
        onToggleIsLoading: () -> Unit,
        onGetMyself: () -> Unit) -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)