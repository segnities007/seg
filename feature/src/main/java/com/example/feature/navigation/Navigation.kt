package com.example.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.presentation.navigation.NavigationRoute
import com.example.feature.navigation.hub.NavigationHub
import com.example.feature.navigation.login.NavigationLogin
import com.example.feature.screens.splash.Splash

@Composable
fun TopNavigation() {
    val topNavController: NavHostController = rememberNavController()

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
                onTopNavigate = { route ->
                    // go to login
                    topNavController.navigate(route)
                },
            )
        }
    }
}
