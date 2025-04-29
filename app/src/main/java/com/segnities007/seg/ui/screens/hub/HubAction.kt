package com.segnities007.seg.ui.screens.hub

import com.example.domain.model.Post
import com.example.domain.model.User

sealed class HubAction {
    data object ChangeIsHideTopBar : HubAction()

    data object ResetIsHideTopBar : HubAction()

    data object GetUser : HubAction()

    data class SetSelf(
        val newSelf: User,
    ) : HubAction()

    data class SetComment(
        val comment: Post,
    ) : HubAction()

    data class SetUserID(
        val userID: String,
    ) : HubAction()

    data class SetAccounts(
        val accounts: List<String>,
    ) : HubAction()

    data class AddPostIDToMyLikes(
        val postID: Int,
    ) : HubAction()

    data class RemovePostIDFromMyLikes(
        val postID: Int,
    ) : HubAction()

    data class AddPostIDFromReposts(
        val postID: Int,
    ) : HubAction()

    data class RemovePostIDFromReposts(
        val postID: Int,
    ) : HubAction()

    data class ChangeCurrentRouteName(
        val currentRouteName: String,
    ) : HubAction()
}
