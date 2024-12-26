package com.segnities007.seg.ui.screens.login

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.components.bottom_bar.LoginBottomBar
import com.segnities007.seg.ui.components.top_bar.LoginTopBar
import com.segnities007.seg.ui.screens.login.sign_in.SignIn
import com.segnities007.seg.ui.screens.login.sign_up.SignUp

@Composable
fun Login(
    loginUiState: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
){
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {}
        }
    ) {
        LoginUi(
            signUiState = loginUiState.signUiState,
            navigateUiState = loginUiState.navigateUiState,
            onEmailChange = loginUiState::onEmailChange,
            onPasswordChange = loginUiState::onPasswordChange,
            onChangeIndex = loginUiState::onIndexChange,
            onSignInWithEmailPassword = {loginUiState.onSignInWithEmailPassword(navController)},
            onLoginWithGoogle = loginUiState::onLoginWithGoogle,
            onSignUpWithEmailPassword = {loginUiState.onSignUpWithEmailPassword(navController)}
        )
    }
}

@Composable
private fun LoginUi(
    signUiState: SignUiState,
    navigateUiState: NavigateUiState,
    onChangeIndex: (Int) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInWithEmailPassword: () -> Unit,
    onSignUpWithEmailPassword: () -> Unit,
    onLoginWithGoogle: (Context) -> Unit,
){
    Scaffold(
        topBar = { LoginTopBar {  } },
        bottomBar = { LoginBottomBar(currentIndex = navigateUiState.index, onClick = onChangeIndex) },
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