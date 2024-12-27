package com.segnities007.seg.ui.screens.hub

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
import com.segnities007.seg.R
import com.segnities007.seg.data.model.bottom_bar.HubItem
import com.segnities007.seg.ui.components.bottom_bar.BottomBar
import com.segnities007.seg.ui.screens.hub.home.Home
import com.segnities007.seg.ui.screens.hub.notify.Notify
import com.segnities007.seg.ui.screens.hub.post.Post
import com.segnities007.seg.ui.screens.hub.search.Search
import com.segnities007.seg.ui.screens.hub.setting.Setting

@Composable
fun Hub(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    hubViewModel: HubViewModel = hiltViewModel(),
){
    ModalNavigationDrawer(
        modifier = modifier,
        drawerContent = {
//            ModalDrawerSheet {
//                NavigationDrawerItem(
//                    label = { Text(text = "Drawer Item") },
//                    selected = false,
//                    onClick = { /*TODO*/ }
//                )
//            }
        }
    ) {
        HubUi(
            navigateUiState = hubViewModel.navigateUiState,
            onIndexChange = hubViewModel::onIndexChange,
        )
    }
}

@Composable
private fun HubUi(
    navigateUiState: NavigateUiState,
    onIndexChange: (Int) -> Unit,
){
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                contentDescription = stringResource(R.string.menu_description),
            ) { }
        },
        bottomBar = {
            BottomBar(
                items = HubItem(),
                currentIndex = navigateUiState.index,
            ) { onIndexChange(it) }
        }
    ){ innerPadding ->
        when(navigateUiState.index){
            0 -> Home(modifier = Modifier.padding(innerPadding))
            1 -> Search(modifier = Modifier.padding(innerPadding))
            2 -> Post(modifier = Modifier.padding(innerPadding))
            3 -> Notify(modifier = Modifier.padding(innerPadding))
            4 -> Setting(modifier = Modifier.padding(innerPadding))
        }
    }
}


