package com.example.feature.screens.login

fun loginReducer(
    state: LoginState,
    action: LoginAction,
): LoginState =
    when (action) {
        LoginAction.ResetIsFailedSignIn,
        -> state.copy(isFailedSignIn = false)

        LoginAction.ChangeIsFailedSignIn,
        -> state.copy(isFailedSignIn = !state.isFailedSignIn)

        is LoginAction.ChangeEmail,
        -> state.copy(email = action.email)

        is LoginAction.ChangePassword,
        -> state.copy(password = action.password)

        is LoginAction.ChangeCurrentRouteName,
        -> state.copy(currentRouteName = action.newCurrentRouteName)

        is LoginAction.OpenSnackBar -> {
            state.copy(
                snackBarMessage = action.message,
                isShowSnackBar = true,
            )
        }

        LoginAction.CloseSnackBar -> {
            state.copy(
                isShowSnackBar = false,
            )
        }

        else -> state
    }
