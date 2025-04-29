package com.segnities007.seg.ui.screens.hub.setting.my_posts

import androidx.compose.runtime.Immutable
import com.example.domain.model.Post
import com.example.domain.model.User

@Immutable
sealed class MyPostsAction {
    data object Init : MyPostsAction()

    data object GetPosts : MyPostsAction()

    data object GetLikedPosts : MyPostsAction()

    data object GetRepostedPosts : MyPostsAction()

    data class UpdateSelectedTabIndex(
        val index: Int,
    ) : MyPostsAction()

    data class SetSelf(
        val self: User,
    ) : MyPostsAction()

    data class RemovePostFromPosts(
        val post: Post,
    ) : MyPostsAction()

    data class ProcessOfEngagement(
        val newPost: Post,
    ) : MyPostsAction()
}
