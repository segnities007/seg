package com.example.feature.screens.hub.setting.userinfo

import androidx.compose.runtime.Immutable
import com.example.domain.model.user.User

@Immutable
sealed interface UserInfoAction {
    data class UpdateUser(
        val user: User,
    ) : UserInfoAction

    data class ChangeDescription(
        val newDescription: String,
    ) : UserInfoAction

    data class ChangeName(
        val newName: String,
    ) : UserInfoAction

    data class ChangeUserID(
        val newUserID: String,
    ) : UserInfoAction
}
