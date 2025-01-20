package com.segnities007.seg.ui.components.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.Route

@Composable
fun BottomBar(
    items: BottomBarItem,
    currentRouteName: String,
    onNavigate: (Route) -> Unit,
) {
    NavigationBar {
        items.unSelectedIconIDs.forEachIndexed { index, _ ->
            val routeName = items.routes[index].name
            NavigationBarItem(
                icon = {
                    Icon(
                        painter =
                            if (routeName == currentRouteName) {
                                painterResource(items.selectedIconIDs[index])
                            } else {
                                painterResource(items.unSelectedIconIDs[index])
                            },
                        contentDescription = currentRouteName,
                    )
                },
                label = { Text(routeName) },
                selected = routeName == currentRouteName,
                onClick = { onNavigate(items.routes[index]) },
            )
        }
    }
}
