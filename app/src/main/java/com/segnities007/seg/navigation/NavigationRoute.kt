package com.segnities007.seg.navigation

import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.Routes
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute(val routeName: String) : Route {

    @Serializable
    data object Splash : NavigationRoute("Splash")

    @Serializable
    data object Login : NavigationRoute("Login")

    @Serializable
    data object Hub : NavigationRoute("Hub")

    companion object : Routes {
        override val routeList: List<NavigationRoute> = listOf(
        )
    }
}
