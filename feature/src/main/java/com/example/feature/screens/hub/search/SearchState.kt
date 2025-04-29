package com.example.feature.screens.hub.search

import com.example.domain.model.Post
import com.example.domain.model.User

data class SearchState(
    val users: List<User> = listOf(),
    val posts: List<Post> = listOf(),
    val postsSortedByViewCount: List<Post> = listOf(),
)
