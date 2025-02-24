package com.segnities007.seg.ui.components.card.postcard

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

data class DefaultPostCardScope(
    override val post: Post,
    override val hubUiState: HubUiState,
    override val hubUiAction: HubUiAction,
    override val postCardUiAction: PostCardUiAction,
    override val onHubNavigate: (NavigationHubRoute) -> Unit,
) : PostCardScope
