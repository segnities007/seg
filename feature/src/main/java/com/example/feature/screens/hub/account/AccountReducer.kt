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

        else -> state
    }
