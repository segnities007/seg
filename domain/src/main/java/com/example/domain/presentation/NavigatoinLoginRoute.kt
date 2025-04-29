package com.example.domain.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationLoginRoute(
    override val name: String,
) : Navigation {
    @Serializable
    data object SignIn : NavigationLoginRoute(name = "SignIn")

    @Serializable
    data object SignUp : NavigationLoginRoute(name = "SignUp")

    @Serializable
    data object ConfirmEmail : NavigationLoginRoute(name = "ConfirmEmail")

    @Serializable
    data object CreateAccount : NavigationLoginRoute(name = "CreateAccount")
}
