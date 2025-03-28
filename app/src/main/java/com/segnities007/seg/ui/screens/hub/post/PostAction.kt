package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.User
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
@Immutable
sealed class PostAction{
    data class UpdateIsLoading(val isLoading: Boolean): PostAction()
    data class UpdateInputText(val newInputText: String): PostAction()
    data class CreatePost(
        val user: User,
        val onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        val onUpdateSelf: () -> Unit,
        val onNavigate: (NavigationHubRoute) -> Unit,
    ): PostAction()
    data class CreateComment(
        val hubState: HubState,
        val onHubAction: (HubAction) -> Unit,
        val onUpdateIsLoading: (isLoading: Boolean) -> Unit,
        val onNavigate: (NavigationHubRoute) -> Unit,
    ): PostAction()
}