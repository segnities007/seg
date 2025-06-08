package com.example.feature.screens.hub

import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.feature.model.UiStatus

data class HubState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val self: User = User(),
    val otherUserID: String = "",
    val accounts: List<String> = listOf(),
    val currentRouteName: String = "Home",
    val comment: Post = Post(),
    val snackBarMessage: String = "Error is Nothing.",
    val isShowSnackBar: Boolean = false,
    val isHideTopBar: Boolean = false,
)
