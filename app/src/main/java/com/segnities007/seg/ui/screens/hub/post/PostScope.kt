package com.segnities007.seg.ui.screens.hub.post

import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction

interface PostScope {
    val homeAction: HomeAction
    val hubState: HubState
    val hubAction: HubAction
    val postState: PostState
    val postAction: PostAction
    val onHubNavigate: (Navigation) -> Unit
}
