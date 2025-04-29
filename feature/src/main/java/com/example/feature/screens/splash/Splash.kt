package com.example.feature.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.presentation.NavigationRoute

@Composable
fun Splash(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    LaunchedEffect(Unit) {
        splashViewModel.hasLogged(
            onNavigateToHost = { navHostController.navigate(NavigationRoute.Hub) },
            onNavigateToLogin = { navHostController.navigate(NavigationRoute.Login) },
        )
    }
}
