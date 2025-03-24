package com.segnities007.seg.ui.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val currentRouteName: String = "SignIn",
    val isFailedSignIn: Boolean = false,
)