package com.segnities007.seg.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.navigation.NavigationRoute

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
