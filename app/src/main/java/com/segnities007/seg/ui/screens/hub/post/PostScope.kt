package com.segnities007.seg.ui.screens.hub.post

import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.home.HomeUiAction

interface PostScope {
    val homeUiAction: HomeUiAction
    val hubUiState: HubUiState
    val hubUiAction: HubUiAction
    val postUiState: PostUiState
    val postUiAction: PostUiAction
    val onHubNavigate: (Navigation) -> Unit
}
