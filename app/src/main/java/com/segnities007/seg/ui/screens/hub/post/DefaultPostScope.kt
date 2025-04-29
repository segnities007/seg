package com.segnities007.seg.ui.screens.hub.post

import com.example.domain.presentation.Navigation
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction

data class DefaultPostScope(
    override val hubState: HubState,
    override val postState: PostState,
    override val onHubAction: (HubAction) -> Unit,
    override val onHomeAction: (HomeAction) -> Unit,
    override val onPostAction: (PostAction) -> Unit,
    override val onHubNavigate: (Navigation) -> Unit,
) : PostScope
