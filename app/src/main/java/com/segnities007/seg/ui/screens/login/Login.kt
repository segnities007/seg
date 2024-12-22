package com.segnities007.seg.ui.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.segnities007.seg.ui.screens.login.sign_in.SignIn
import com.segnities007.seg.ui.screens.login.sign_up.SignUp

@Composable
fun Login(
    loginUiState: LoginViewModel = viewModel(),
){
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {}
        }
    ) {
        LoginUi(
            uiState = loginUiState.signUiState,
            onEmailChange = loginUiState::onEmailChange,
            onPasswordChange = loginUiState::onPasswordChange,
        )
    }
}

@Composable
private fun LoginUi(
    signUiState: SignUiState,
    navigateUiState: NavigateUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
){
    Scaffold(
        topBar = {},
        bottomBar = {},
    ){innerPadding ->
        when(navigateUiState.index){
            0 -> SignIn(
                    modifier = Modifier.padding(innerPadding),
                    uiState = signUiState,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange
                )
            1 -> SignUp(
                    modifier = Modifier.padding(innerPadding),
                    uiState = signUiState,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange
                )
        }
    }
}