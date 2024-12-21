package com.segnities007.seg.ui.components.navigation_drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SegNavigationDrawer(
    modifier: Modifier = Modifier,
    textPadding: Dp = 16.dp,
    thisViewModel: SegNavigationDrawerViewModel = SegNavigationDrawerViewModel(),
    content: @Composable () -> Unit,
){

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(thisViewModel.drawerState.value),
        drawerContent = {
            ModalDrawerSheet {
                DrawerSheet(textPadding = textPadding)
            }
        }
    ) {
        content()
    }

}

@Composable
private fun DrawerSheet(
    modifier: Modifier = Modifier,
    textPadding: Dp,
){
    Text("Drawer title", modifier = Modifier.padding(textPadding))
    HorizontalDivider()
    NavigationDrawerItem(
        label = { Text(text = "Drawer Item") },
        selected = false,
        onClick = { /*TODO*/ }
    )
}