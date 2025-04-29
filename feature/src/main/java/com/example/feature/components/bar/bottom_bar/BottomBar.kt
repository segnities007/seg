package com.example.feature.components.bar.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.domain.model.BottomBarItem
import com.example.domain.presentation.Navigation

@Composable
fun BottomBar(
    items: BottomBarItem,
    currentRouteName: String,
    onNavigate: (Navigation) -> Unit,
) {
    NavigationBar {
        items.routes.forEachIndexed { index, route ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter =
                            if (route.name == currentRouteName) {
                                painterResource(items.selectedIconIDs[index])
                            } else {
                                painterResource(items.unSelectedIconIDs[index])
                            },
                        contentDescription = currentRouteName,
                    )
                },
                label = { Text(route.name) },
                selected = route.name == currentRouteName,
                onClick = { onNavigate(route) },
            )
        }
    }
}
