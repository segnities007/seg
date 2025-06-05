package com.example.feature.screens.login.sign_up.create_account

fun createAccountReducer(
    state: CreateAccountState,
    action: CreateAccountAction,
): CreateAccountState =
    when (action) {
        CreateAccountAction.OpenDatePicker,
        -> state.copy(isShow = true)

        CreateAccountAction.CloseDatePicker,
        -> state.copy(isShow = false)

        is CreateAccountAction.ChangeName,
        -> state.copy(name = action.name)

        is CreateAccountAction.ChangeUserID,
        -> state.copy(userID = action.userID)

        is CreateAccountAction.ChangeBirthday,
        -> state.copy(birthday = action.birthday)

        is CreateAccountAction.SetUri,
        -> state.copy(uri = action.uri)

        is CreateAccountAction.SetPicture,
        -> state.copy(path = action.path, byteArray = action.byteArray)

        else -> state
    }
