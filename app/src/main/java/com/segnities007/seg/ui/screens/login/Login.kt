package com.segnities007.seg.ui.screens.login

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawerViewModel

@Composable
fun Login(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigationDrawerViewModel: NavigationDrawerViewModel = hiltViewModel(),
    navController: NavHostController,
){
    NavigationDrawer(
        items = LoginItem(),
        onIndexChange = loginViewModel::onIndexChange,
        onDrawerClose = navigationDrawerViewModel::closeDrawer
    ) {
        LoginUi(
            signUiState = loginViewModel.signUiState,
            navigateUiState = loginViewModel.navigateUiState,
            onEmailChange = loginViewModel::onEmailChange,
            onPasswordChange = loginViewModel::onPasswordChange,
            onChangeIndex = loginViewModel::onIndexChange,
            onSignInWithEmailPassword = {loginViewModel.onSignInWithEmailPassword(navController)},
            onLoginWithGoogle = loginViewModel::onLoginWithGoogle,
            onSignUpWithEmailPassword = {loginViewModel.onSignUpWithEmailPassword(navController)},
            onDrawerOpen = navigationDrawerViewModel::openDrawer
        )
    }
}

@Composable
private fun LoginUi(
    signUiState: SignUiState,
    navigateUiState: NavigateUiState,
    onDrawerOpen: suspend () -> Unit,
    onChangeIndex: (Int) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInWithEmailPassword: () -> Unit,
    onSignUpWithEmailPassword: () -> Unit,
    onLoginWithGoogle: (Context) -> Unit,
){
    Scaffold(
        topBar = {
                TopBar(
                    title = stringResource(R.string.login_screen_title),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = onDrawerOpen
                    )
                 },
        bottomBar = {
                BottomBar(
                    currentIndex = navigateUiState.index,
                    items = LoginItem(),
                    onClick = onChangeIndex
                    )
                    },
    ){innerPadding ->
        when(navigateUiState.index){
            0 -> SignIn(
                    modifier = Modifier.padding(innerPadding),
                    signUiState = signUiState,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onSignInWithEmailPassword = onSignInWithEmailPassword,
                    onLoginWithGoogle = onLoginWithGoogle,
                )
            1 -> SignUp(
                    modifier = Modifier.padding(innerPadding),
                    signUiState = signUiState,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onSignUpWithEmailPassword = onSignUpWithEmailPassword,
                    onLoginWithGoogle = onLoginWithGoogle,
                )
        }
    }
}