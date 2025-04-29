package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.Immutable
import com.example.domain.model.User
import com.example.domain.presentation.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Immutable
sealed class PostAction {
    data class UpdateIsLoading(
        val isLoading: Boolean,
    ) : PostAction()

    data class UpdateInputText(
        val newInputText: String,
    ) : PostAction()

    data class CreatePost(
        val user: User,
        val onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        val onUpdateSelf: () -> Unit,
        val onNavigate: (NavigationHubRoute) -> Unit,
    ) : PostAction()

    data class CreateComment(
        val hubState: HubState,
        val onHubAction: (HubAction) -> Unit,
        val onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        val onNavigate: (NavigationHubRoute) -> Unit,
    ) : PostAction()
}
