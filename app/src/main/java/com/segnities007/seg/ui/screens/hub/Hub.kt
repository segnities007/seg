package com.segnities007.seg.ui.screens.hub

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.segnities007.seg.ui.screens.hub.setting.Setting
import com.segnities007.seg.ui.screens.hub.trend.Trend
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
            postUiAction = hubViewModel.getPostUiAction(),
            postUiState = hubViewModel.postUiState,
            drawerAction = hubViewModel.getDrawerAction(),
            navController = navController
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubUi(
    navController: NavHostController,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
    navigateState: NavigateState,
    navigateAction: NavigateAction,
    drawerAction: DrawerAction,
){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when(navigateState.index){
                0 -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = drawerAction.openDrawer,
                    scrollBehavior = scrollBehavior,
                )
                1 -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = drawerAction.openDrawer,
                    scrollBehavior = scrollBehavior,
                )
                3 -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = drawerAction.openDrawer,
                    scrollBehavior = scrollBehavior,
                )
                4 -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = drawerAction.openDrawer,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        bottomBar = {
            BottomBar(
                items = HubItem(),
                currentIndex = navigateState.index,
            ) { navigateAction.onIndexChange(it) }
        },
        floatingActionButton = {
            when(navigateState.index){
                0 -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                1 -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                3 -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                4 -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
            }
        }
    ){ innerPadding ->
        when(navigateState.index){
            0 -> Home(modifier = Modifier.padding(innerPadding))
            1 -> Trend(modifier = Modifier.padding(innerPadding))
            2 -> Post(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                postUiState = postUiState,
                postUiAction = postUiAction,
            )
            3 -> Notify(modifier = Modifier.padding(innerPadding))
            4 -> Setting(modifier = Modifier.padding(innerPadding))
        }
    }
}


