package com.segnities007.seg.ui.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.components.top_bar.TopBar
import com.segnities007.seg.ui.screens.login.sign_in.SignIn
import com.segnities007.seg.ui.screens.login.sign_up.SignUp
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.LoginItem
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawerAction
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawerViewModel
import com.segnities007.seg.ui.screens.login.sign_up.ConfirmEmail
import com.segnities007.seg.ui.screens.login.sign_up.CreateAccount

@Composable
fun Login(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigationDrawerViewModel: NavigationDrawerViewModel = hiltViewModel(),
    navController: NavHostController,
){
    NavigationDrawer(
        items = LoginItem(),
        navigateAction = loginViewModel.getNavigateAction(),
        navigationDrawerAction = navigationDrawerViewModel.getNavigationDrawerAction(),
    ) {
        LoginUi(
            navController = navController,
            signUiState = loginViewModel.signUiState,
            signUiAction = loginViewModel.getSignUiAction(),
            navigateUiState = loginViewModel.navigateUiState,
            navigateUiAction = loginViewModel.getNavigationUiAction(),
            createAccountUiState = loginViewModel.createAccountUiState,
            createAccountUiAction = loginViewModel.getCreateAccountUiAction(),
            navigationDrawerAction = navigationDrawerViewModel.getNavigationDrawerAction(),
            confirmEmailUiAction = loginViewModel.getConfirmEmailUiAction(),
        )
    }
}

@Composable
private fun LoginUi(
    navController: NavHostController,
    signUiState: SignUiState,
    navigateUiState: NavigateState,
    signUiAction: SignUiAction,
    navigateUiAction: NavigateAction,
    navigationDrawerAction: NavigationDrawerAction,
    createAccountUiState: CreateAccountUiState,
    createAccountUiAction: CreateAccountUiAction,
    confirmEmailUiAction: ConfirmEmailUiAction,
){
    Scaffold(
        topBar = {
                TopBar(
                    screenIndex = navigateUiState.index,
                    title = stringResource(R.string.login_screen_title),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = navigationDrawerAction.openDrawer
                )
                 },
        bottomBar = {
            if (navigateUiState.index != 2 && navigateUiState.index != 3){
                BottomBar(
                        currentIndex = navigateUiState.index,
                        items = LoginItem(),
                        onClick = navigateUiAction.onIndexChange
                        )
            }
                    },
    ){innerPadding ->
        when(navigateUiState.index){
            0 -> SignIn(
                modifier = Modifier.padding(innerPadding),
                signUiState = signUiState,
                signUiAction = signUiAction,
                navController = navController,
            )
            1 -> SignUp(
                    modifier = Modifier.padding(innerPadding),
                    signUiState = signUiState,
                    signUiAction = signUiAction,
                )
            2 -> ConfirmEmail(confirmEmailUiAction = confirmEmailUiAction)
            3 -> CreateAccount(
                navController = navController,
                createAccountUiAction = createAccountUiAction,
                createAccountUiState = createAccountUiState,
            )
        }
    }
}