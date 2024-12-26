package com.segnities007.seg

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.login.Login
import com.segnities007.seg.ui.screens.splash.Splash
import kotlinx.serialization.Serializable

@Serializable
object  Splash
@Serializable
object Login
@Serializable
object Hub


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
){

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            Splash(navController = navController)
        }
        composable<Login> {
            Login(navController = navController)
        }
        composable<Hub>{
            Home()
        }
    }

}