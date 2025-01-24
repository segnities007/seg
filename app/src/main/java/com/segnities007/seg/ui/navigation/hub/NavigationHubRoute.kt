package com.segnities007.seg.ui.navigation.hub

import com.segnities007.seg.domain.presentation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationHubRoute : Route {
    @Serializable
    data class Home(
        override val name: String = "Home",
    ) : NavigationHubRoute()

    @Serializable
    data class Trend(
        override val name: String = "Trend",
    ) : NavigationHubRoute()

    @Serializable
    data class Post(
        override val name: String = "Post",
    ) : NavigationHubRoute()

    @Serializable
    data class Notify(
        override val name: String = "Notify",
    ) : NavigationHubRoute()

    @Serializable
    data class Setting(
        override val name: String = "Setting",
    ) : NavigationHubRoute()

    @Serializable
    data class Account(
        override val name: String = "Account",
    ) : NavigationHubRoute()

    @Serializable
    data class Accounts(
        override val name: String = "Accounts",
    ) : NavigationHubRoute()

    @Serializable
    data class Search(
        override val name: String = "Search",
    ) : NavigationHubRoute()

    @Serializable
    data class Comment(
        override val name: String = "Comment",
    ) : NavigationHubRoute()

    companion object {
        val routes: List<Route> =
            listOf(
                Home(),
                Trend(),
                Post(),
                Notify(),
                Setting(),
                Account(),
                Accounts(),
                Search(),
                Comment(),
            )
    }
}
