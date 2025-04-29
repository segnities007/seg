package com.example.feature.components.card.postcard

import com.example.domain.model.Post
import com.example.domain.presentation.NavigationHubRoute
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

data class DefaultPostCardScope(
    override val post: Post,
    override val hubState: HubState,
    override val onHubAction: (HubAction) -> Unit,
    override val onPostCardAction: (PostCardAction) -> Unit,
    override val onHubNavigate: (NavigationHubRoute) -> Unit,
) : PostCardScope
