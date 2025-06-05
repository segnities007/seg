package com.example.feature.screens.hub.search

fun searchReducer(
    state: SearchState,
    action: SearchAction,
): SearchState =
    when (action) {
        SearchAction.ResetSearchState ->
            state.copy(
                users = listOf(),
                posts = listOf(),
                postsSortedByViewCount = listOf(),
            )

        is SearchAction.ProcessOfEngagementAction -> {
            var newPosts =
                state.posts.map { post ->
                    if (action.newPost.id == post.id) action.newPost else post
                }

            val newState = state.copy(posts = newPosts)

            newPosts =
                newState.postsSortedByViewCount.map { post ->
                    if (action.newPost.id == post.id) action.newPost else post
                }

            newState.copy(postsSortedByViewCount = newPosts)
        }

        else -> state
    }
