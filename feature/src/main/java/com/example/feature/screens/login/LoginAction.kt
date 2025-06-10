package com.example.feature.screens.login

sealed interface LoginAction {
    data object ChangeIsFailedSignIn : LoginAction

    data object ResetIsFailedSignIn : LoginAction

    data class ChangeCurrentRouteName(
        val newCurrentRouteName: String,
    ) : LoginAction

    data class ChangePassword(
        val password: String,
    ) : LoginAction

    data class ChangeEmail(
        val email: String,
    ) : LoginAction

    data class SignUpWithEmailPassword(
        val onNavigate: () -> Unit,
        val onLoginAction: (LoginAction) -> Unit,
    ) : LoginAction

    data class SignInWithEmailPassword(
        val onNavigate: () -> Unit,
        val onLoginAction: (LoginAction) -> Unit,
    ) : LoginAction

    data class OpenSnackBar(
        val message: String,
    ) : LoginAction

    data object CloseSnackBar : LoginAction
}
