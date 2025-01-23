package com.segnities007.seg.navigation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Route
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
) {
    val navBackStackEntry by loginNavHostController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringBefore("?") // クエリパラメータを除去
            ?.substringAfterLast(".") // 最後のドット以降を取得

    Login(
        topAction = loginViewModel.onGetTopAction(),
        currentRouteName = currentRoute.toString(),
        topState = loginViewModel.topState,
        onNavigate = { route: Route ->
            loginNavHostController.navigate(route)
            loginViewModel.getLoginAction().onChangeCurrentRouteName(route.name)
        },
    ) { modifier: Modifier ->
        NavHost(
            navController = loginNavHostController,
            startDestination = NavigationLoginRoute.SignIn(),
        ) {
            composable<NavigationLoginRoute.SignIn> {
                SignIn(
                    modifier = modifier,
                    loginUiState = loginViewModel.loginUiState,
                    loginUiAction = loginViewModel.getLoginAction(),
                    onNavigate = { navHostController.navigate(NavigationRoute.Hub()) },
                )
            }
            composable<NavigationLoginRoute.SignUp> {
                SignUp(
                    modifier = modifier,
                    loginUiState = loginViewModel.loginUiState,
                    loginUiAction = loginViewModel.getLoginAction(),
                    onNavigateToConfirmEmail = {
                        loginNavHostController.navigate(
                            NavigationLoginRoute.ConfirmEmail(),
                        )
                    },
                )
            }
            composable<NavigationLoginRoute.ConfirmEmail> {
                ConfirmEmail(
                    modifier = modifier,
                    confirmEmailUiAction = loginViewModel.getConfirmEmailUiAction(),
                    onNavigateToCreateAccount = {
                        loginNavHostController.navigate(
                            NavigationLoginRoute.CreateAccount(),
                        )
                    },
                )
            }
            composable<NavigationLoginRoute.CreateAccount> {
                CreateAccount(
                    modifier = modifier,
                    createAccountUiState = loginViewModel.createAccountUiState,
                    createAccountUiAction = loginViewModel.getCreateAccountUiAction(),
                    onNavigateToHub = { navHostController.navigate(NavigationRoute.Hub()) },
                )
            }
        }
    }
}
