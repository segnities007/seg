package com.segnities007.seg.navigation.login

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.navigation.NavigationRoute
import com.segnities007.seg.ui.screens.login.Login
import com.segnities007.seg.ui.screens.login.LoginViewModel
import com.segnities007.seg.ui.screens.login.sign_in.SignIn
import com.segnities007.seg.ui.screens.login.sign_up.ConfirmEmail
import com.segnities007.seg.ui.screens.login.sign_up.CreateAccount
import com.segnities007.seg.ui.screens.login.sign_up.SignUp

@Composable
fun NavigationLogin(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    loginNavHostController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel = hiltViewModel(),
){
    NavHost(
        navController = loginNavHostController,
        startDestination = NavigationLoginRoute.SignIn,
    ) {
        composable<NavigationLoginRoute.SignIn> {
            Login(
                navHostController = navHostController,
                loginNavHostController = loginNavHostController,
                topAction = loginViewModel.onGetTopAction(),
                topState = loginViewModel.topState
            ) { modifier: Modifier ->
                SignIn(
                    modifier = modifier,
                    loginUiState = loginViewModel.loginUiState,
                    loginUiAction = loginViewModel.getLoginAction(),
                    onNavigate = {navHostController.navigate(NavigationRoute.Hub)}
                )
            }
        }
        composable<NavigationLoginRoute.SignUp> {
            Login(
                navHostController = navHostController,
                loginNavHostController = loginNavHostController,
                topAction = loginViewModel.onGetTopAction(),
                topState = loginViewModel.topState
            ) { modifier: Modifier ->
                SignUp(
                    modifier = modifier,
                    loginUiState = loginViewModel.loginUiState,
                    loginUiAction = loginViewModel.getLoginAction(),
                    onNavigateToConfirmEmail = {loginNavHostController.navigate(NavigationLoginRoute.ConfirmEmail)}
                )
            }
        }
        composable<NavigationLoginRoute.ConfirmEmail>{
            Login(
                navHostController = navHostController,
                loginNavHostController = loginNavHostController,
                topAction = loginViewModel.onGetTopAction(),
                topState = loginViewModel.topState
            ) { modifier: Modifier ->
                ConfirmEmail(
                    modifier = modifier,
                    confirmEmailUiAction = loginViewModel.getConfirmEmailUiAction(),
                    onNavigateToCreateAccount = {loginNavHostController.navigate(NavigationLoginRoute.CreateAccount)},
                )
            }
        }
        composable<NavigationLoginRoute.CreateAccount>{
            Login(
                navHostController = navHostController,
                loginNavHostController = loginNavHostController,
                topAction = loginViewModel.onGetTopAction(),
                topState = loginViewModel.topState
            ) { modifier: Modifier ->
                CreateAccount(
                    modifier = modifier,
                    createAccountUiState = loginViewModel.createAccountUiState,
                    createAccountUiAction = loginViewModel.getCreateAccountUiAction(),
                    onNavigateToHub = {navHostController.navigate(NavigationRoute.Hub)},
                )
            }
        }

    }
}