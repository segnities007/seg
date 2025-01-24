package com.segnities007.seg.ui.navigation.login

import com.segnities007.seg.domain.presentation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationLoginRoute : Route {
    @Serializable
    data class SignIn(
        override val name: String = "SignIn",
    ) : NavigationLoginRoute()

    @Serializable
    data class SignUp(
        override val name: String = "SignUp",
    ) : NavigationLoginRoute()

    @Serializable
    data class ConfirmEmail(
        override val name: String = "ConfirmEmail",
    ) : NavigationLoginRoute()

    @Serializable
    data class CreateAccount(
        override val name: String = "CreateAccount",
    ) : NavigationLoginRoute()

    companion object {
        val routes: List<Route> =
            listOf(
                SignIn(),
                SignUp(),
                ConfirmEmail(),
                CreateAccount(),
            )
    }
}
