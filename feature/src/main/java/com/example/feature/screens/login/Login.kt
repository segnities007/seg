package com.example.feature.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.feature.navigation.TopAction
import com.example.feature.navigation.TopState

@Composable
fun Login(
    topState: TopState,
    currentRouteName: String,
    onTopAction: (TopAction) -> Unit,
    onNavigate: (Navigation) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawer(
        items = BottomBarLoginItem(),
        drawerState = topState.drawerState,
        onNavigate = onNavigate,
        onDrawerClose = { onTopAction(TopAction.CloseDrawer) },
    ) {
        LoginUi(
            currentRouteName = currentRouteName,
            onTopAction = onTopAction,
            onNavigate = onNavigate,
            content = content,
        )
    }
}

@Composable
private fun LoginUi(
    currentRouteName: String,
    onTopAction: (TopAction) -> Unit,
    onNavigate: (Navigation) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
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
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTopBar(
    currentRouteName: String,
    onTopAction: (TopAction) -> Unit,
) {
    TopBar(
        titleContent = { Text(text = stringResource(R.string.login_screen_title)) },
        routeName = currentRouteName,
        onDrawerOpen = { onTopAction(TopAction.OpenDrawer) },
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
