package com.segnities007.seg.ui.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.segnities007.seg.ui.components.top_bar.TopBar
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.LoginItem
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.domain.presentation.TopState
import com.segnities007.seg.navigation.login.NavigationLoginRoute
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer

@Composable
fun Login(
    modifier: Modifier = Modifier,
    topAction: TopAction,
    topState: TopState,
    onNavigate: (Route) -> Unit,
    currentRouteName: String,
    content: @Composable (Modifier) -> Unit,
){

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
){
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.login_screen_title),
                routeName = currentRouteName,
                onDrawerOpen = topAction.openDrawer,
            )
        },
        bottomBar = {
            when(currentRouteName){
                NavigationLoginRoute.SignIn.routeName -> BottomBar(
                    items = LoginItem(),
                    currentRouteName = currentRouteName,
                    onNavigate = onNavigate,
                )
                NavigationLoginRoute.SignUp.routeName -> BottomBar(
                    items = LoginItem(),
                    currentRouteName = currentRouteName,
                    onNavigate = onNavigate,
                )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
    ){innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}