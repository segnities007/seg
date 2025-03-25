package com.segnities007.seg.ui.screens.login

sealed class LoginAction {
    data object ChangeIsFailedSignIn : LoginAction()

    data object ResetIsFailedSignIn : LoginAction()

    data class ChangeCurrentRouteName(
        val newCurrentRouteName: String,
    ) : LoginAction()

    data class ChangePassword(
        val password: String,
    ) : LoginAction()

    data class ChangeEmail(
        val email: String,
    ) : LoginAction()

    data class SignUpWithEmailPassword(
        val onNavigate: () -> Unit,
    ) : LoginAction()

    data class SignInWithEmailPassword(
        val onNavigate: () -> Unit,
    ) : LoginAction()
}
