package com.segnities007.seg.ui.navigation.hub

import com.segnities007.seg.domain.presentation.Navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationHubRoute(
    override val name: String,
) : Navigation {
    @Serializable data object Home : NavigationHubRoute(name = "Home")

    @Serializable data object Trend : NavigationHubRoute(name = "Trend")

    @Serializable data object Post : NavigationHubRoute(name = "Post")

    @Serializable data object Notify : NavigationHubRoute(name = "Notify")

    @Serializable data object Setting : NavigationHubRoute(name = "Setting")

    @Serializable data object Account : NavigationHubRoute(name = "Account")

    @Serializable data object Accounts : NavigationHubRoute(name = "Accounts")

    @Serializable data object Search : NavigationHubRoute(name = "Search")

    @Serializable data object Comment : NavigationHubRoute(name = "Comment")
}
