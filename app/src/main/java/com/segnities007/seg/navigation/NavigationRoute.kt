package com.segnities007.seg.navigation

import com.segnities007.seg.domain.presentation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute : Route {
    @Serializable
    data class Splash(
        override val name: String = "Splash",
    ) : NavigationRoute()

    @Serializable
    data class Login(
        override val name: String = "Login",
    ) : NavigationRoute()

    @Serializable
    data class Hub(
        override val name: String = "Hub",
    ) : NavigationRoute()

    companion object{
        val routes: List<Route> =
            listOf(
                Splash(),
                Login(),
                Hub(),
            )
    }
}
