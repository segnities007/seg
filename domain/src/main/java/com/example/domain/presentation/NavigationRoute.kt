package com.example.domain.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute(
    override val name: String,
) : Navigation {
    @Serializable
    data object Splash : NavigationRoute(name = "Splash")

    @Serializable
    data object Login : NavigationRoute(name = "Login")

    @Serializable
    data object Hub : NavigationRoute(name = "Hub")
}
