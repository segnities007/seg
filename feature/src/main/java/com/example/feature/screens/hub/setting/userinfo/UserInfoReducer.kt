package com.example.feature.screens.hub.setting.userinfo

fun userInfoReducer(
    state: UserInfoState,
    action: UserInfoAction,
): UserInfoState =
    when (action) {
        is UserInfoAction.ChangeDescription ->
            state.copy(description = action.newDescription)

        is UserInfoAction.ChangeName ->
            state.copy(name = action.newName)

        is UserInfoAction.ChangeUserID ->
            state.copy(userID = action.newUserID)

        else -> state
    }
