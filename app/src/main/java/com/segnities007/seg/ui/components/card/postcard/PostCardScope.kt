package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Stable
interface PostCardScope {
    val post: Post
    val hubUiState: HubUiState
    val hubUiAction: HubUiAction
    val postCardUiAction: PostCardUiAction
    val onHubNavigate: (NavigationHubRoute) -> Unit
}
