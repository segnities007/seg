package com.segnities007.seg.ui.components.navigation_drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NavigationDrawerViewModel : ViewModel() {

    var drawerState by mutableStateOf(DrawerState(initialValue = DrawerValue.Closed))
        private set

    suspend fun openDrawer(){
        drawerState.open()
    }

    suspend fun closeDrawer(){
        drawerState.close()

    }

}
