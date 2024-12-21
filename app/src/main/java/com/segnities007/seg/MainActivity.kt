package com.segnities007.seg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.data.model.Navigation
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

    NavHost(navController = navController, startDestination = Navigation.Login) {
        composable<Navigation.Splash> {
            Splash()
        }
        composable<Navigation.Login> {
            Login()
        }
        composable<Navigation.Hub>{
            Home()
        }
    }

}