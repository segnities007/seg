package com.example.feature.screens.hub

import com.example.domain.model.post.Post
import com.example.domain.model.user.User

sealed interface HubAction {
    data object ChangeIsHideTopBar : HubAction

    data object ResetIsHideTopBar : HubAction

    data class GetUser(
        val onHubAction: (HubAction) -> Unit,
    ) : HubAction

    data class SetSelf(
        val newSelf: User,
        override val onHubAction: (HubAction) -> Unit,
    ) : ReturnHubAction(onHubAction)

    data class AddPostIDToMyLikes(
        val postID: Int,
        override val onHubAction: (HubAction) -> Unit,
    ) : ReturnHubAction(onHubAction)

    data class RemovePostIDFromMyLikes(
        val postID: Int,
        override val onHubAction: (HubAction) -> Unit,
    ) : ReturnHubAction(onHubAction)

    data class AddPostIDFromReposts(
        val postID: Int,
        override val onHubAction: (HubAction) -> Unit,
    ) : ReturnHubAction(onHubAction)

    data class RemovePostIDFromReposts(
        val postID: Int,
        override val onHubAction: (HubAction) -> Unit,
    ) : ReturnHubAction(onHubAction)

    data class SetComment(
        val comment: Post,
    ) : HubAction

    data class SetUserID(
        val userID: String,
    ) : HubAction

    data class SetAccounts(
        val accounts: List<String>,
    ) : HubAction

    data class ChangeCurrentRouteName(
        val currentRouteName: String,
    ) : HubAction

    data class OpenSnackBar(
        val message: String,
    ) : HubAction

    data object CloseSnackBar : HubAction

    open class ReturnHubAction(
        open val onHubAction: (HubAction) -> Unit,
    ) : HubAction
}
