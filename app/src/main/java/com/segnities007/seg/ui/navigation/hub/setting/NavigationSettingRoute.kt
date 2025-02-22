package com.segnities007.seg.ui.navigation.hub.setting

import com.segnities007.seg.domain.presentation.Navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationSettingRoute(
    override val name: String,
) : Navigation {
    @Serializable data object Preference : NavigationSettingRoute(name = "Preference")

    @Serializable data object UserInfo : NavigationSettingRoute(name = "UserInfo")

    @Serializable data object Posts : NavigationSettingRoute(name = "Posts")
}
