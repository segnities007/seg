package com.segnities007.seg.ui.screens.hub.setting.userinfo

import com.segnities007.seg.data.model.User

data class UserInfoAction(
    val onUserUpdate: suspend (user: User) -> Unit,
    val onDescriptionChange: (newDescription: String) -> Unit,
    val onNameChange: (newName: String) -> Unit,
    val onUserIDChange: (newUserID: String) -> Unit,
)
