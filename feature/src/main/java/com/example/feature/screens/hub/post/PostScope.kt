package com.example.feature.screens.hub.post

import com.example.domain.presentation.navigation.Navigation
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction

interface PostScope {
    val hubState: HubState
    val postState: PostState
    val onHubAction: (HubAction) -> Unit
    val onHomeAction: (HomeAction) -> Unit
    val onPostAction: (PostAction) -> Unit
    val onHubNavigate: (Navigation) -> Unit
}
