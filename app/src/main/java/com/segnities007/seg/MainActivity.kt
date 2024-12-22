package com.segnities007.seg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.data.model.Hub
import com.segnities007.seg.data.model.Login
import com.segnities007.seg.data.model.Splash
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.login.Login
import com.segnities007.seg.ui.screens.splash.Splash
import com.segnities007.seg.ui.theme.SegTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SegTheme {
                Router()
            }
        }
    }
}


@Composable
fun Router(
    navController: NavHostController = rememberNavController(),
){

    NavHost(navController = navController, startDestination = Login) {
        composable<Splash> {
            Splash()
        }
        composable<Login> {
            Login()
        }
        composable<Hub>{
            Home()
        }
    }

}