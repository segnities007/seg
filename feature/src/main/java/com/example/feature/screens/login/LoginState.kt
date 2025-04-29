package com.example.feature.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val currentRouteName: String = "SignIn",
    val isFailedSignIn: Boolean = false,
)
