package com.segnities007.seg.navigation.hub

import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.Routes
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationHubRoute(val routeName: String) : Route {

    @Serializable
    data object Home : NavigationHubRoute("Home")

    @Serializable
    data object Trend : NavigationHubRoute("Trend")

    @Serializable
    data object Post : NavigationHubRoute("Post")

    @Serializable
    data object Notify : NavigationHubRoute("Notify")

    @Serializable
    data object Setting : NavigationHubRoute("Setting")

    @Serializable
    data object Account : NavigationHubRoute("Account")

    @Serializable
    data object Accounts : NavigationHubRoute("Accounts")

    @Serializable
    data object Search : NavigationHubRoute("Search")

    companion object : Routes {
        override val routeList: List<NavigationHubRoute> = listOf(
            Home,
            Trend,
            Post,
            Notify,
            Setting,
            Account,
            Accounts,
            Search
        )
    }
}
