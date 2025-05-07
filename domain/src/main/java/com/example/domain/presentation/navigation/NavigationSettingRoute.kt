package com.example.domain.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationSettingRoute(
    override val name: String,
) : Navigation {
    @Serializable
    data object Preference : NavigationSettingRoute(name = "Preference")

    @Serializable
    data object UserInfo : NavigationSettingRoute(name = "UserInfo")

    @Serializable
    data object Posts : NavigationSettingRoute(name = "Posts")
}
