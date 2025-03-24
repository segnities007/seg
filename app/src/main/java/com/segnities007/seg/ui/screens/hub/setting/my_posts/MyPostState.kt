package com.segnities007.seg.ui.screens.hub.setting.my_posts

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

@Immutable
data class MyPostsState(
    val self: User = User(),
    val posts: List<Post> = listOf(),
    val likedPosts: List<Post> = listOf(),
    val repostedPosts: List<Post> = listOf(),
    val hasNoMorePosts: Boolean = false,
    val hasNoMoreLikedPosts: Boolean = false,
    val hasNoMoreRepostedPosts: Boolean = false,
    val titles: List<String> =
        listOf(
            "Post",
            "Like",
            "Repost",
        ),
    val selectedTabIndex: Int = 0,
)
