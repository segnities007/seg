package com.example.feature.screens.login

import com.example.feature.model.UiStatus

data class LoginState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val email: String = "",
    val password: String = "",
    val currentRouteName: String = "SignIn",
    val isFailedSignIn: Boolean = false,
)
