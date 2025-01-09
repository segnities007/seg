package com.segnities007.seg.ui.screens.hub

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.components.top_bar.TopBar
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.HubItem
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.domain.presentation.TopState
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.components.floating_button.FloatingButton
import com.segnities007.seg.ui.components.navigation_drawer.NavigationDrawer
import com.segnities007.seg.ui.components.top_bar.TopStatusBar
import com.segnities007.seg.ui.screens.hub.account.Account
import com.segnities007.seg.ui.screens.hub.setting.Setting
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.trend.Trend

@Composable
fun Hub(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    hubViewModel: HubViewModel = hiltViewModel(),
){

    LaunchedEffect(Unit) {
        val action = hubViewModel.getTopAction()
        action.onNavigate(NavigationIndex.HubHome)
    }

    val indices = listOf(
        NavigationIndex.HubHome,
        NavigationIndex.HubTrend,
        NavigationIndex.HubPost,
        NavigationIndex.HubNotify,
        NavigationIndex.HubSetting
    )

    NavigationDrawer(
        items = HubItem(),
        indices = indices,
        topState = hubViewModel.topState,
        topAction = hubViewModel.getTopAction(),
    ) {
        HubUi(
            hubUiState = hubViewModel.hubUiState,
            hubUiAction = hubViewModel.getHubUiAction(),
            indices = indices,
            topState = hubViewModel.topState,
            topAction = hubViewModel.getTopAction(),
            navController = navController,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubUi(
    navController: NavHostController,
    indices: List<NavigationIndex>,
    topState: TopState,
    topAction: TopAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when(topState.index){
                NavigationIndex.HubHome -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = topAction.openDrawer,
                    scrollBehavior = scrollBehavior,
                    currentIndex = topState.index,
                )
                NavigationIndex.HubTrend -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = topAction.openDrawer,
                    currentIndex = topState.index,
                )
                NavigationIndex.HubNotify -> TopBar(
                    title = stringResource(R.string.app_name),
                    contentDescription = stringResource(R.string.menu_description),
                    onDrawerOpen = topAction.openDrawer,
                    currentIndex = topState.index,
                )
                NavigationIndex.HubSetting -> TopStatusBar(
                    user = hubUiState.user,
                )
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        },
        bottomBar = {
            BottomBar(
                items = HubItem(),
                currentIndex = topState.index,
                indices = indices,
            ) { hubUiAction.onNavigate(it) }
        },
        floatingActionButton = {
            when(topState.index){
                NavigationIndex.HubHome -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                NavigationIndex.HubTrend -> FloatingButton(iconID = R.drawable.baseline_search_24) { }
                else -> Spacer(modifier = Modifier.padding(0.dp))
            }
        }
    ){ innerPadding ->
        when(topState.index){
            NavigationIndex.HubHome -> Home(
                modifier = Modifier.padding(innerPadding),
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
            )
            NavigationIndex.HubTrend -> Trend(modifier = Modifier.padding(innerPadding))
            NavigationIndex.HubPost -> Post(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                hubUiState = hubUiState
            )
            NavigationIndex.HubNotify -> Notify(modifier = Modifier.padding(innerPadding))
            NavigationIndex.HubSetting -> Setting(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
            )
            NavigationIndex.HubAccount -> Account(
                modifier = Modifier.padding(innerPadding),
                hubUiState = hubUiState,
                hubUiAction = hubUiAction,
            )
            else -> Spacer(modifier = Modifier.padding(0.dp))
        }
    }
}


