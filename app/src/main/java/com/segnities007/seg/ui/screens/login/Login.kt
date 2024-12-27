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

@Composable
fun Login(
    loginUiState: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
){
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {

            }
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
        topBar = {
                TopBar(
                            title = stringResource(R.string.login_screen_title),
                            contentDescription = stringResource(R.string.menu_description),
                            onClick = {/* TODO */}
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