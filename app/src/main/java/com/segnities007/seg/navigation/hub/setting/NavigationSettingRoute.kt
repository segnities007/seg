package com.segnities007.seg.navigation.hub.setting

import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.Routes
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationSettingRoute(val routeName: String) : Route {

    @Serializable
    data object Preference : NavigationSettingRoute("Preference")

    @Serializable
    data object UserInfo : NavigationSettingRoute("UserInfo")

    companion object : Routes {
        override val routeList: List<NavigationSettingRoute> = listOf(
            Preference,
            UserInfo,
        )
    }
}
