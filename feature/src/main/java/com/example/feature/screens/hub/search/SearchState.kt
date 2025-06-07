package com.example.feature.screens.hub.search

import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.feature.model.UiStatus

data class SearchState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val users: List<User> = listOf(),
    val posts: List<Post> = listOf(),
    val postsSortedByViewCount: List<Post> = listOf(),
)
