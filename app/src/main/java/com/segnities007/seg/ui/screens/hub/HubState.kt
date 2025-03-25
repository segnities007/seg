package com.segnities007.seg.ui.screens.hub

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

data class HubState(
    val user: User = User(), // myself
    val userID: String = "", // other user's userID
    val accounts: List<String> = listOf(),
    val currentRouteName: String = "Home",
    val comment: Post = Post(),
    val isHideTopBar: Boolean = false,
)
