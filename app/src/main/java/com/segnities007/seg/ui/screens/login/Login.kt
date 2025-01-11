package com.segnities007.seg.ui.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.components.top_bar.TopBar
import com.segnities007.seg.ui.screens.login.sign_in.SignIn
import com.segnities007.seg.ui.screens.login.sign_up.SignUp
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.LoginItem
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.domain.presentation.TopState
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.screens.login.sign_up.ConfirmEmail
import com.segnities007.seg.ui.screens.login.sign_up.CreateAccount

@Composable
fun Login(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
){
    val indices = listOf(
        NavigationIndex.LoginSignIn,
        NavigationIndex.LoginSignUp,
    )

    LaunchedEffect(Unit) {
        val action = loginViewModel.getTopAction()
        action.onNavigate(NavigationIndex.LoginSignIn)
    }

    NavigationDrawer(
        items = LoginItem(),
        indices = indices,
        topState = loginViewModel.topState,
        topAction = loginViewModel.getTopAction(),
    ) {
        LoginUi(
            navController = navController,
            indices = indices,
            topState = loginViewModel.topState,
            topAction = loginViewModel.getTopAction(),
            signUiState = loginViewModel.signUiState,
            signUiAction = loginViewModel.getSignUiAction(),
            createAccountUiState = loginViewModel.createAccountUiState,
            createAccountUiAction = loginViewModel.getCreateAccountUiAction(),
            confirmEmailUiAction = loginViewModel.getConfirmEmailUiAction(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginUi(
    navController: NavHostController,
    indices: List<NavigationIndex>,
    topState: TopState,
    topAction: TopAction,
    signUiState: SignUiState,
    signUiAction: SignUiAction,
    createAccountUiState: CreateAccountUiState,
    createAccountUiAction: CreateAccountUiAction,
    confirmEmailUiAction: ConfirmEmailUiAction,
){
    Scaffold(
        topBar = {
                TopBar(
                    currentIndex = topState.index,
                    title = stringResource(R.string.login_screen_title),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = topAction.openDrawer,
                )
                 },
        bottomBar = {
            when(topState.index){
                NavigationIndex.LoginSignIn -> BottomBar(
                    currentIndex = topState.index,
                    items = LoginItem(),
                    onClick = topAction.onNavigate,
                    indices = indices,
                )
                NavigationIndex.LoginSignUp -> BottomBar(
                    currentIndex = topState.index,
                    items = LoginItem(),
                    onClick = topAction.onNavigate,
                    indices = indices,
                )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
    ){innerPadding ->
        when(topState.index){
            NavigationIndex.LoginSignIn -> SignIn(
                modifier = Modifier.padding(innerPadding),
                signUiState = signUiState,
                signUiAction = signUiAction,
                navController = navController,
            )
            NavigationIndex.LoginSignUp -> SignUp(
                    modifier = Modifier.padding(innerPadding),
                    signUiState = signUiState,
                    signUiAction = signUiAction,
                )
            NavigationIndex.LoginSignUpConfirmEmail -> ConfirmEmail(confirmEmailUiAction = confirmEmailUiAction)
            NavigationIndex.LoginSignUpCreateAccount -> CreateAccount(
                navController = navController,
                createAccountUiAction = createAccountUiAction,
                createAccountUiState = createAccountUiState,
            )
            else -> Spacer(modifier = Modifier.padding(0.dp))
        }
    }
}