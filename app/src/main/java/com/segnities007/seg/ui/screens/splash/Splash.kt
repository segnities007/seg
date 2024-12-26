package com.segnities007.seg.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun Splash(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navController: NavHostController,
){
    LaunchedEffect(Unit) {
        splashViewModel.hasLogined(navController)
    }
}

