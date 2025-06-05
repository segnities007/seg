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

        is MyPostsAction.ProcessOfEngagement -> {
            val newPost = action.newPost

            val newPosts =
                state.posts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            val newLikedPosts =
                state.likedPosts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            val newRepostedPosts =
                state.repostedPosts.map { post ->
                    if (newPost.id == post.id) newPost else post
                }

            state.copy(
                posts = newPosts,
                likedPosts = newLikedPosts,
                repostedPosts = newRepostedPosts,
            )
        }

        is MyPostsAction.RemovePostFromPosts,
        -> state.copy(posts = state.posts.minus(action.post))

        is MyPostsAction.SetSelf,
        -> state.copy(self = action.self)

        is MyPostsAction.UpdateSelectedTabIndex,
        -> state.copy(selectedTabIndex = action.index)
    }
