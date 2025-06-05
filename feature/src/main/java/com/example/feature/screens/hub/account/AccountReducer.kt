package com.example.feature.screens.hub.account

fun accountReducer(
    action: AccountAction,
    state: AccountState,
): AccountState =
    when (action) {
        AccountAction.ToggleIsLoading,
        is AccountAction.ClickFollowButton,
        -> state.copy(isLoading = !state.isLoading)

        AccountAction.ResetState,
        -> state.copy(posts = listOf(), isCompletedFetchPosts = false)

        is AccountAction.SetOtherUser,
        -> state.copy(user = action.user)

        is AccountAction.ProcessOfEngagementAction -> {
            val newPosts =
                state.posts.map { post ->
                    if (action.newPost.id == post.id) action.newPost else post
                }

            state.copy(posts = newPosts)
        }

        is AccountAction.GetUserPosts,
        is AccountAction.GetPosts,
        -> state.copy(isCompletedFetchPosts = !state.isCompletedFetchPosts)

        else -> state
    }
