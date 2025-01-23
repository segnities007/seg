package com.segnities007.seg.navigation.hub.setting

import com.segnities007.seg.domain.presentation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationSettingRoute : Route {
    @Serializable
    data class Preference(
        override val name: String = "Preference",
    ) : NavigationSettingRoute()

    @Serializable
    data class UserInfo(
        override val name: String = "UserInfo",
    ) : NavigationSettingRoute()

    @Serializable
    data class Posts(
        override val name: String = "Posts",
    ) : NavigationSettingRoute()

    companion object {
        val routes: List<Route> =
            listOf(
                Preference(),
                UserInfo(),
                Posts(),
            )
    }
}
