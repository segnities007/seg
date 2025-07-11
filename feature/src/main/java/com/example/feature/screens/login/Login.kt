package com.example.feature.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.presentation.bottombar.BottomBarLoginItem
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationLoginRoute
import com.example.feature.R
import com.example.feature.components.bar.bottom_bar.BottomBar
import com.example.feature.components.bar.top_bar.TopBar
import com.example.feature.components.navigation_drawer.NavigationDrawer
import com.example.feature.navigation.TopLayerAction
import com.example.feature.navigation.TopLayerState

@Composable
fun Login(
    topLayerState: TopLayerState,
    currentRouteName: String,
    loginState: LoginState,
    onTopAction: (TopLayerAction) -> Unit,
    onNavigate: (Navigation) -> Unit,
    onLoginAction: (LoginAction) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawer(
        items = BottomBarLoginItem(),
        drawerState = topLayerState.drawerState,
        onNavigate = onNavigate,
        onDrawerClose = { onTopAction(TopLayerAction.CloseDrawer) },
    ) {
        LoginUi(
            loginState = loginState,
            currentRouteName = currentRouteName,
            onTopAction = onTopAction,
            onNavigate = onNavigate,
            onLoginAction = onLoginAction,
            content = content,
        )
    }
}

@Composable
private fun LoginUi(
    loginState: LoginState,
    currentRouteName: String,
    onTopAction: (TopLayerAction) -> Unit,
    onNavigate: (Navigation) -> Unit,
    onLoginAction: (LoginAction) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loginState.isShowSnackBar) {
        if (loginState.isShowSnackBar == true) {
            snackBarHostState.showSnackbar(
                message = loginState.snackBarMessage,
                duration = SnackbarDuration.Long,
                withDismissAction = true,
            )
            onLoginAction(LoginAction.CloseSnackBar)
        }
    }

    Scaffold(
        topBar = {
            LoginTopBar(
                currentRouteName = currentRouteName,
                onTopAction = onTopAction,
            )
        },
        bottomBar = {
            LoginBottomBar(
                currentRouteName = currentRouteName,
                onNavigate = onNavigate,
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.sizeIn(maxWidth = 400.dp),
            )
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTopBar(
    currentRouteName: String,
    onTopAction: (TopLayerAction) -> Unit,
) {
    TopBar(
        titleContent = { Text(text = stringResource(R.string.login_screen_title)) },
        routeName = currentRouteName,
        onDrawerOpen = { onTopAction(TopLayerAction.OpenDrawer) },
    )
}

@Composable
private fun LoginBottomBar(
    currentRouteName: String,
    onNavigate: (Navigation) -> Unit,
) {
    when (currentRouteName) {
        NavigationLoginRoute.SignIn.name ->
            BottomBar(
                items = BottomBarLoginItem(),
                currentRouteName = currentRouteName,
                onNavigate = onNavigate,
            )

        NavigationLoginRoute.SignUp.name ->
            BottomBar(
                items = BottomBarLoginItem(),
                currentRouteName = currentRouteName,
                onNavigate = onNavigate,
            )

        else -> Spacer(modifier = Modifier.padding(0.dp))
    }
}
