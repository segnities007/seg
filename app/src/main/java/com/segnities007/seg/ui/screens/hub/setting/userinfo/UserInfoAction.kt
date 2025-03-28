package com.segnities007.seg.ui.screens.hub.setting.userinfo

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.User

//
// data class UserInfoAction(
//    val onUserUpdate: suspend (user: User) -> Unit,
//    val onDescriptionChange: (newDescription: String) -> Unit,
//    val onNameChange: (newName: String) -> Unit,
//    val onUserIDChange: (newUserID: String) -> Unit,
// )

@Immutable
sealed class UserInfoAction {
    data class UpdateUser(
        val user: User,
    ) : UserInfoAction()

    data class ChangeDescription(
        val newDescription: String,
    ) : UserInfoAction()

    data class ChangeName(
        val newName: String,
    ) : UserInfoAction()

    data class ChangeUserID(
        val newUserID: String,
    ) : UserInfoAction()
}
