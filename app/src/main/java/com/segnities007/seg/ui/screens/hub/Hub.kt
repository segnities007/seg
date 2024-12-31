package com.segnities007.seg.ui.screens.hub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.components.top_bar.TopBar
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.HubItem
import com.segnities007.seg.domain.presentation.DrawerAction
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.floating_button.FloatingButton
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.search.Search
import com.segnities007.seg.ui.screens.hub.setting.Setting
import com.segnities007.seg.ui.screens.login.NavigateAction
import com.segnities007.seg.ui.screens.login.NavigateState

@Composable
fun Hub(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    hubViewModel: HubViewModel = hiltViewModel(),
){

    NavigationDrawer(
        items = HubItem(),
        navigateAction = hubViewModel.getNavigateAction(),
        drawerAction = hubViewModel.getDrawerAction(),
        drawerState = hubViewModel.drawerState,
    ) {
        HubUi(
            navigateState = hubViewModel.navigateState,
            navigateAction = hubViewModel.getNavigateAction(),
            drawerAction = hubViewModel.getDrawerAction(),
        )
    }

}

@Composable
private fun HubUi(
    navigateState: NavigateState,
    navigateAction: NavigateAction,
    drawerAction: DrawerAction,
){
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                contentDescription = stringResource(R.string.menu_description),
                onDrawerOpen = drawerAction.openDrawer,
            )
        },
        bottomBar = {
            BottomBar(
                items = HubItem(),
                currentIndex = navigateState.index,
            ) { navigateAction.onIndexChange(it) }
        },
        floatingActionButton = {
            when(navigateState.index){
                0 -> FloatingButton(iconID = R.drawable.baseline_home_24) { }
                1 -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                2 -> FloatingButton(iconID = R.drawable.baseline_post_add_24) { }
                3 -> FloatingButton(iconID = R.drawable.baseline_notifications_24) { }
                4 -> FloatingButton(iconID = R.drawable.baseline_settings_24) { }
            }
        }
    ){ innerPadding ->
        when(navigateState.index){
            0 -> Home(modifier = Modifier.padding(innerPadding))
            1 -> Search(modifier = Modifier.padding(innerPadding))
            2 -> Post(modifier = Modifier.padding(innerPadding))
            3 -> Notify(modifier = Modifier.padding(innerPadding))
            4 -> Setting(modifier = Modifier.padding(innerPadding))
        }
    }
}


