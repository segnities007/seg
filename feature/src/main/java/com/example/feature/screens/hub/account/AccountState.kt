package com.example.feature.screens.hub.account

import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.feature.model.UiStatus

data class AccountState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val isCompletedFetchPosts: Boolean = false,
    val isLoading: Boolean = false,
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
)
