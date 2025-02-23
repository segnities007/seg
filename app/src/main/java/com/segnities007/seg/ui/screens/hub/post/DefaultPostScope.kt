package com.segnities007.seg.ui.screens.hub.post

import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.home.HomeUiAction

data class DefaultPostScope(
    override val homeUiAction: HomeUiAction,
    override val hubUiState: HubUiState,
    override val hubUiAction: HubUiAction,
    override val postUiState: PostUiState,
    override val postUiAction: PostUiAction,
    override val onHubNavigate: (Navigation) -> Unit,
) : PostScope
