package com.segnities007.seg.navigation.login

import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.Routes
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationLoginRoute(
    val routeName: String,
) : Route {
    @Serializable
    data object SignIn : NavigationLoginRoute("SignIn")

    @Serializable
    data object SignUp : NavigationLoginRoute("SignUp")

    @Serializable
    data object ConfirmEmail : NavigationLoginRoute("ConfirmEmail")

    @Serializable
    data object CreateAccount : NavigationLoginRoute("CreateAccount")

    companion object : Routes {
        override val routeList: List<NavigationLoginRoute> =
            listOf(
                SignIn,
                SignUp,
                ConfirmEmail,
                CreateAccount,
            )
    }
}
