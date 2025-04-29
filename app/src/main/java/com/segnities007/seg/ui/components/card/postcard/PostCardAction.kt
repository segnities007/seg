package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Immutable
import com.example.domain.model.Post
import com.example.domain.presentation.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction
import com.segnities007.seg.ui.screens.hub.setting.my_posts.MyPostsAction

@Immutable
sealed class PostCardAction {
    data class DeletePost(
        val post: Post,
        val onMyPostsAction: (MyPostsAction) -> Unit,
        val onHomeAction: (HomeAction) -> Unit,
        val hubState: HubState,
        val onHubAction: (HubAction) -> Unit,
    ) : PostCardAction()

    data class ClickAvatarIcon(
        val onHubNavigate: (NavigationHubRoute) -> Unit,
    ) : PostCardAction()

    data class ClickPostCard(
        val onHubNavigate: (NavigationHubRoute) -> Unit,
    ) : PostCardAction()

    data class ClickLikeIcon(
        val post: Post,
        val hubState: HubState,
        val onHubAction: (HubAction) -> Unit,
        val onProcessOfEngagementAction: (newPost: Post) -> Unit,
    ) : PostCardAction()

    data class ClickRepostIcon(
        val post: Post,
        val hubState: HubState,
        val onHubAction: (HubAction) -> Unit,
        val onProcessOfEngagementAction: (newPost: Post) -> Unit,
    ) : PostCardAction()

    data class IncrementViewCount(
        val post: Post,
    ) : PostCardAction()
}
