package com.example.feature.screens.hub.account

import com.example.domain.model.Post
import com.example.domain.model.User

data class AccountState(
    val user: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
)
