package com.segnities007.seg.ui.components.navigation_drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class NavigationDrawerAction(
    val openDrawer: suspend () -> Unit,
    val closeDrawer: suspend () -> Unit,
)

class NavigationDrawerViewModel : ViewModel() {

    var drawerState by mutableStateOf(DrawerState(initialValue = DrawerValue.Closed))
        private set

    fun getNavigationDrawerAction(): NavigationDrawerAction{
        return NavigationDrawerAction(
            openDrawer = this::openDrawer,
            closeDrawer = this::closeDrawer,
        )
    }

    private suspend fun openDrawer(){
        drawerState.open()
    }

    private suspend fun closeDrawer(){
        drawerState.close()

    }

}
