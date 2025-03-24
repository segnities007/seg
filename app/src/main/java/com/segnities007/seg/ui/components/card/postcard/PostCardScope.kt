package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Stable
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Stable
interface PostCardScope {
    val post: Post
    val hubState: HubState
    val hubAction: HubAction
    val postCardUiAction: PostCardUiAction
    val onHubNavigate: (NavigationHubRoute) -> Unit
}
