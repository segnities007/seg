package com.example.feature.screens.hub.post

import androidx.compose.runtime.Immutable
import com.example.domain.model.post.Genre
import com.example.domain.model.user.User
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Immutable
sealed interface PostAction {
    data class UpdateIsLoading(
        val isLoading: Boolean,
    ) : PostAction

    data class UpdateGenre(
        val newGenre: Genre,
    ) : PostAction

    data class UpdateInputText(
        val newInputText: String,
    ) : PostAction

    data class CreatePost(
        val user: User,
        val onUpdateSelf: () -> Unit,
        val onNavigate: (NavigationHubRoute) -> Unit,
        val onHubAction: (HubAction) -> Unit,
    ) : PostAction

    data class CreateComment(
        val hubState: HubState,
        val onNavigate: (NavigationHubRoute) -> Unit,
        val onHubAction: (HubAction) -> Unit,
    ) : PostAction
}
