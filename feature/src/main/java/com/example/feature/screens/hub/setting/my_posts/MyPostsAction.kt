package com.example.feature.screens.hub.setting.my_posts

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Post
import com.example.domain.model.user.User
import com.example.feature.screens.hub.HubAction

@Immutable
sealed interface MyPostsAction {
    data object Init : MyPostsAction

    data class GetPosts(
        val onHubAction: (HubAction) -> Unit,
    ) : MyPostsAction

    data class GetLikedPosts(
        val onHubAction: (HubAction) -> Unit,
    ) : MyPostsAction

    data class GetRepostedPosts(
        val onHubAction: (HubAction) -> Unit,
    ) : MyPostsAction

    data class UpdateSelectedTabIndex(
        val index: Int,
    ) : MyPostsAction

    data class SetSelf(
        val self: User,
    ) : MyPostsAction

    data class RemovePostFromPosts(
        val post: Post,
    ) : MyPostsAction

    data class ProcessOfEngagement(
        val newPost: Post,
    ) : MyPostsAction
}
