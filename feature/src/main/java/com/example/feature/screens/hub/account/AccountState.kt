package com.example.feature.screens.hub.account

import com.example.domain.model.User
import com.example.domain.model.post.Post

data class AccountState(
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
)
