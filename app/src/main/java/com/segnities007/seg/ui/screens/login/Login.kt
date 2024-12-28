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
            signUiState = loginViewModel.signUiState,
            signUiAction = loginViewModel.getSignUiAction(),
            navigateUiState = loginViewModel.navigateUiState,
            navigateUiAction = loginViewModel.getNavigationUiAction(),
            createAccountUiState = loginViewModel.createAccountUiState,
            createAccountUiAction = loginViewModel.getCreateAccountUiAction(),
            navigationDrawerAction = navigationDrawerViewModel.getNavigationDrawerAction(),
        )
    }
}

@Composable
private fun LoginUi(
    signUiState: SignUiState,
    navigateUiState: NavigateState,
    signUiAction: SignUiAction,
    navigateUiAction: NavigateAction,
    navigationDrawerAction: NavigationDrawerAction,
    createAccountUiState: CreateAccountUiState,
    createAccountUiAction: CreateAccountUiAction,

){
    Scaffold(
        topBar = {
                TopBar(
                        title = stringResource(R.string.login_screen_title),
                        contentDescription = stringResource(R.string.menu_description),
                        onDrawerOpen = navigationDrawerAction.openDrawer
                        )
                 },
        bottomBar = {
                BottomBar(
                        currentIndex = navigateUiState.index,
                        items = LoginItem(),
                        onClick = navigateUiAction.onIndexChange
                        )
                    },
    ){innerPadding ->
        when(navigateUiState.index){
            0 -> SignIn(
                    modifier = Modifier.padding(innerPadding),
                    signUiState = signUiState,
                    signUiAction = signUiAction,
                )
            1 -> SignUp(
                    modifier = Modifier.padding(innerPadding),
                    signUiState = signUiState,
                    signUiAction = signUiAction,
                )
            2 -> CreateAccount(
                    createAccountUiAction = createAccountUiAction,
                    createAccountUiState = createAccountUiState,
                )
        }
    }
}