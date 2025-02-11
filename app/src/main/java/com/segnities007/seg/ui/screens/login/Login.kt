package com.segnities007.seg.ui.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.LoginItem
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.domain.presentation.TopState
import com.segnities007.seg.ui.components.bar.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.components.bar.top_bar.TopBar
import com.segnities007.seg.ui.navigation.login.NavigationLoginRoute

@Composable
fun Login(
    topAction: TopAction,
    topState: TopState,
    onNavigate: (Route) -> Unit,
    currentRouteName: String,
    content: @Composable (Modifier) -> Unit,
) {
    NavigationDrawer(
        items = LoginItem(),
        drawerState = topState.drawerState,
        onNavigate = onNavigate,
        onDrawerClose = topAction.closeDrawer,
    ) {
        LoginUi(
            topAction = topAction,
            currentRouteName = currentRouteName,
            onNavigate = onNavigate,
            content = content,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginUi(
    topAction: TopAction,
    currentRouteName: String,
    onNavigate: (Route) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                titleContent = { Text(text = stringResource(R.string.login_screen_title)) },
                routeName = currentRouteName,
                onDrawerOpen = topAction.openDrawer,
            )
        },
        bottomBar = {
            when (currentRouteName) {
                NavigationLoginRoute.SignIn().name ->
                    BottomBar(
                        items = LoginItem(),
                        currentRouteName = currentRouteName,
                        onNavigate = onNavigate,
                    )
                NavigationLoginRoute.SignUp().name ->
                    BottomBar(
                        items = LoginItem(),
                        currentRouteName = currentRouteName,
                        onNavigate = onNavigate,
                    )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
