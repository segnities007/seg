package com.segnities007.seg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.NavigationHub
import com.segnities007.seg.navigation.login.NavigationLogin
import com.segnities007.seg.ui.screens.splash.Splash

@Composable
fun TopNavigation(topNavController: NavHostController = rememberNavController()) {
    NavHost(
        navController = topNavController,
        startDestination = NavigationRoute.Splash,
    ) {
        composable<NavigationRoute.Splash> {
            Splash(navHostController = topNavController)
        }
        composable<NavigationRoute.Login> {
            NavigationLogin(navHostController = topNavController)
        }
        composable<NavigationRoute.Hub> {
            NavigationHub(
                onTopNavigate = { route: Route ->
                    // go to login
                    topNavController.navigate(route)
                },
            )
        }
    }
}
