package com.segnities007.seg

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.data.model.User
import com.segnities007.seg.ui.screens.hub.Hub
import com.segnities007.seg.ui.screens.login.Login
import com.segnities007.seg.ui.screens.splash.Splash
import kotlinx.serialization.Serializable

@Serializable
object Splash
@Serializable
object Login
@Serializable
object Hub
@Serializable
object ModifyUserInfo

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
){

    NavHost(
        navController = navController,
        startDestination = Splash,
    ) {
        composable<Splash> {
            Splash(navController = navController)
        }
        composable<Login> {
            Login(navController = navController)
        }
        composable<Hub>{
            Hub(navController = navController)
        }
    }

}