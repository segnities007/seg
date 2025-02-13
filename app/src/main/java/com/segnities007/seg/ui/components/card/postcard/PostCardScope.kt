package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Stable
interface PostCardScope {
    val post: Post
    val commonPadding: Dp
    val hubUiState: HubUiState
    val hubUiAction: HubUiAction
    val postCardUiAction: PostCardUiAction
    val onHubNavigate: (Route) -> Unit
}

data class DefaultPostCardScope(
    override val post: Post,
    override val commonPadding: Dp,
    override val hubUiState: HubUiState,
    override val hubUiAction: HubUiAction,
    override val postCardUiAction: PostCardUiAction,
    override val onHubNavigate: (Route) -> Unit,
) : PostCardScope
