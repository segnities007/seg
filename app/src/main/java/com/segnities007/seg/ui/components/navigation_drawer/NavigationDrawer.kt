package com.segnities007.seg.ui.components.navigation_drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.DrawerAction
import com.segnities007.seg.ui.screens.login.NavigateAction
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    items: BottomBarItem,
    navigateAction: NavigateAction,
    drawerState: DrawerState,
    drawerAction: DrawerAction,
    content: @Composable () -> Unit,
){
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                items.labels.forEachIndexed{ index, label ->
                    DrawerSheet(
                        label = label,
                        index = index,
                        painterResourceID = items.unSelectedIconIDs[index],
                        onIndexChange = navigateAction.onIndexChange,
                        onDrawerClose =drawerAction.closeDrawer,
                    )
                }
            }
        }
    ) {
        content()
    }
}

@Composable
private fun DrawerSheet(
    modifier: Modifier = Modifier,
    label: String,
    index: Int,
    painterResourceID: Int,
    onIndexChange: (Int) -> Unit,
    onDrawerClose: suspend () -> Unit,
){

    val scope = rememberCoroutineScope()

    HorizontalDivider()
    NavigationDrawerItem(
        modifier = modifier,
        label = { Text(text = label, maxLines = 1) },
        icon = { Icon(painter = painterResource(painterResourceID), contentDescription = label,) },
        selected = false,
        onClick = {
            onIndexChange(index)
            scope.launch{
                onDrawerClose()
            }
        }
    )
}