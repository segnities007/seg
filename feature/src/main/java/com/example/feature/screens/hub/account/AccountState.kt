package com.example.feature.screens.hub.account

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.feature.model.UiStatus

@Immutable
data class AccountState(
    val isCompletedFetchPosts: Boolean = false,
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
    val indicatorOfPostsStatus: UiStatus = UiStatus.Initial,
    val indicatorOfEngagementStatus: UiStatus = UiStatus.Initial,
    val followButtonStatus: UiStatus = UiStatus.Initial,
)
