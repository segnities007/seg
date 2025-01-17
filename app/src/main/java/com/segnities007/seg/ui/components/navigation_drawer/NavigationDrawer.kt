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
import com.segnities007.seg.domain.presentation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    items: BottomBarItem,
    onNavigate: (route: Route) -> Unit,
    onDrawerClose: suspend () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                items.unSelectedIconIDs.forEachIndexed { index, iconID ->
                    DrawerSheet(
                        route = items.routes.routeList[index],
                        painterResourceID = iconID,
                        onDrawerClose = onDrawerClose,
                        onNavigate = onNavigate,
                    )
                }
            }
        },
    ) {
        content()
    }
}

@Composable
private fun DrawerSheet(
    modifier: Modifier = Modifier,
    route: Route,
    painterResourceID: Int,
    scope: CoroutineScope = rememberCoroutineScope(),
    onNavigate: (route: Route) -> Unit,
    onDrawerClose: suspend () -> Unit,
) {
    HorizontalDivider()
    NavigationDrawerItem(
        modifier = modifier,
        label = { Text(text = route::class.simpleName.toString(), maxLines = 1) },
        icon = {
            Icon(painter = painterResource(painterResourceID), contentDescription = route::class.simpleName.toString())
        },
        selected = false,
        onClick = {
            onNavigate(route)
            scope.launch {
                onDrawerClose()
            }
        },
    )
}
