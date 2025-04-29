package com.segnities007.seg.ui.screens.hub.post

import com.example.domain.presentation.Navigation
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction

interface PostScope {
    val hubState: HubState
    val postState: PostState
    val onHubAction: (HubAction) -> Unit
    val onHomeAction: (HomeAction) -> Unit
    val onPostAction: (PostAction) -> Unit
    val onHubNavigate: (Navigation) -> Unit
}
