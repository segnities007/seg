package com.segnities007.seg.ui.screens.hub.search

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

data class SearchState(
    val users: List<User> = listOf(),
    val posts: List<Post> = listOf(),
    val postsSortedByViewCount: List<Post> = listOf(),
)
