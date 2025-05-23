package com.example.feature.screens.hub

import com.example.domain.model.post.Post
import com.example.domain.model.user.User

data class HubState(
    val user: User = User(), // myself
    val userID: String = "", // other user's userID
    val accounts: List<String> = listOf(),
    val currentRouteName: String = "Home",
    val comment: Post = Post(),
    val isHideTopBar: Boolean = false,
)
