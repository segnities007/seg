package com.segnities007.seg.ui.components.navigation_drawer

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
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.TopAction
import com.segnities007.seg.domain.presentation.TopState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    items: BottomBarItem,
    indices: List<NavigationIndex>,
    topState: TopState,
    topAction: TopAction,
    content: @Composable () -> Unit,
){

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = topState.drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                items.labels.forEachIndexed{ index, label ->
                    DrawerSheet(
                        label = label,
                        index = indices[index],
                        painterResourceID = items.unSelectedIconIDs[index],
                        onIndexChange = topAction.onNavigate,
                        onDrawerClose = topAction.closeDrawer,
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
    index: NavigationIndex,
    painterResourceID: Int,
    onIndexChange: (NavigationIndex) -> Unit,
    onDrawerClose: suspend () -> Unit,
    scope: CoroutineScope = rememberCoroutineScope()
){

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