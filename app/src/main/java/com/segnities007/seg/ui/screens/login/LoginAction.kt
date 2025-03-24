package com.segnities007.seg.ui.screens.login

data class LoginAction(
    val onChangeIsFailedSignIn: () -> Unit,
    val onResetIsFailedSignIn: () -> Unit,
    val onChangeCurrentRouteName: (newCurrentRouteName: String) -> Unit,
    val onPasswordChange: (password: String) -> Unit,
    val onEmailChange: (email: String) -> Unit,
    val onSignUpWithEmailPassword: (onNavigate: () -> Unit) -> Unit,
    val onSignInWithEmailPassword: (onNavigate: () -> Unit) -> Unit,
)