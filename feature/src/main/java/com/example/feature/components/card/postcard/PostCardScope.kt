package com.example.feature.components.card.postcard

import androidx.compose.runtime.Stable
import com.example.domain.model.Post
import com.example.domain.presentation.NavigationHubRoute
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Stable
interface PostCardScope {
    val post: Post
    val hubState: HubState
    val onHubAction: (HubAction) -> Unit
    val onPostCardAction: (PostCardAction) -> Unit
    val onHubNavigate: (NavigationHubRoute) -> Unit
}
