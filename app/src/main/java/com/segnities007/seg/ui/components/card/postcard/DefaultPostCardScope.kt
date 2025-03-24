package com.segnities007.seg.ui.components.card.postcard

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

data class DefaultPostCardScope(
    override val post: Post,
    override val hubState: HubState,
    override val hubAction: HubAction,
    override val postCardUiAction: PostCardUiAction,
    override val onHubNavigate: (NavigationHubRoute) -> Unit,
) : PostCardScope
