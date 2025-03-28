package com.segnities007.seg.ui.navigation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.navigation.NavigationRoute
import com.segnities007.seg.ui.screens.login.Login
import com.segnities007.seg.ui.screens.login.LoginAction
import com.segnities007.seg.ui.screens.login.LoginViewModel
import com.segnities007.seg.ui.screens.login.sign_in.SignIn
import com.segnities007.seg.ui.screens.login.sign_up.SignUp
import com.segnities007.seg.ui.screens.login.sign_up.create_account.CreateAccount

@Composable
fun NavigationLogin(navHostController: NavHostController) {
    val loginNavHostController: NavHostController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val navBackStackEntry by loginNavHostController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringBefore("?") // クエリパラメータを除去
            ?.substringAfterLast(".") // 最後のドット以降を取得

    Login(
        onTopAction = loginViewModel::onTopAction,
        currentRouteName = currentRoute.toString(),
        topState = loginViewModel.topState,
        onNavigate = { route: Navigation ->
            loginNavHostController.navigate(route)
            loginViewModel.onLoginAction(LoginAction.ChangeCurrentRouteName(route.name))
        },
    ) { modifier: Modifier ->
        NavHost(
            navController = loginNavHostController,
            startDestination = NavigationLoginRoute.SignIn,
        ) {
            composable<NavigationLoginRoute.SignIn> {
                SignIn(
                    modifier = modifier,
                    loginState = loginViewModel.loginUiState,
                    loginAction = loginViewModel::onLoginAction,
                    onNavigate = { navHostController.navigate(NavigationRoute.Hub) },
                )
            }
            composable<NavigationLoginRoute.SignUp> {
                SignUp(
                    modifier = modifier,
                    loginState = loginViewModel.loginUiState,
                    loginAction = loginViewModel::onLoginAction,
                    onNavigateToConfirmEmail = {
                        loginNavHostController.navigate(
                            NavigationLoginRoute.CreateAccount,
                        )
                    },
                )
            }
            composable<NavigationLoginRoute.CreateAccount> {
                CreateAccount(
                    modifier = modifier,
                    onNavigateToHub = { navHostController.navigate(NavigationRoute.Hub) },
                )
            }
        }
    }
}
