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
    val onHubAction: (HubAction) -> Unit
    val onPostCardAction: (PostCardAction) -> Unit
    val onHubNavigate: (NavigationHubRoute) -> Unit
}
