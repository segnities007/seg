package com.segnities007.seg.ui.screens.hub.post

import com.segnities007.seg.data.model.User
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

data class PostAction(
    val onUpdateIsLoading: (isLoading: Boolean) -> Unit,
    val onUpdateInputText: (newInputText: String) -> Unit,
    val onCreatePost: (
        user: User,
        onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        onUpdateSelf: () -> Unit,
        onNavigate: () -> Unit,
    ) -> Unit,
    val onCreateComment: (
        hubState: HubState,
        hubAction: HubAction,
        onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        onNavigate: () -> Unit,
    ) -> Unit,
)
