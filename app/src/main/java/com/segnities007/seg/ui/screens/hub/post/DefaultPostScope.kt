package com.segnities007.seg.ui.screens.hub.post

import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction

data class DefaultPostScope(
    override val homeAction: HomeAction,
    override val hubState: HubState,
    override val hubAction: HubAction,
    override val postState: PostState,
    override val postAction: PostAction,
    override val onHubNavigate: (Navigation) -> Unit,
) : PostScope
