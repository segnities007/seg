package com.example.feature.screens.hub.post

import com.example.domain.presentation.navigation.Navigation
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction

data class DefaultPostScope(
    override val hubState: HubState,
    override val postState: PostState,
    override val onHubAction: (HubAction) -> Unit,
    override val onHomeAction: (HomeAction) -> Unit,
    override val onPostAction: (PostAction) -> Unit,
    override val onHubNavigate: (Navigation) -> Unit,
) : PostScope
