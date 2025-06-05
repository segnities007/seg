package com.example.feature.screens.hub.setting.my_posts

fun myPostsReducer(
    state: MyPostsState,
    action: MyPostsAction,
): MyPostsState =
    when (action) {
        MyPostsAction.GetPosts ->
            state.copy(hasNoMorePosts = !state.hasNoMorePosts)

        MyPostsAction.GetRepostedPosts ->
            state.copy(hasNoMoreRepostedPosts = !state.hasNoMoreRepostedPosts)

        MyPostsAction.GetLikedPosts ->
            state.copy(hasNoMoreLikedPosts = !state.hasNoMoreLikedPosts)

        MyPostsAction.Init -> {
            var newState = state
            if (state.self.likes.isEmpty()) {
                newState = newState.copy(hasNoMorePosts = !state.hasNoMorePosts)
            }
            if (state.self.reposts.isEmpty()) {
                newState = newState.copy(hasNoMoreLikedPosts = !state.hasNoMoreLikedPosts)
            }
            if (state.self.posts.isEmpty()) {
                newState = newState.copy(hasNoMoreRepostedPosts = !state.hasNoMoreRepostedPosts)
            }
            newState
        }

        else -> state
    }
