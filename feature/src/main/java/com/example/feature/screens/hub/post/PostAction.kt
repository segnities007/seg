package com.example.feature.screens.hub.post

import androidx.compose.runtime.Immutable
import com.example.domain.model.user.User
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

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
