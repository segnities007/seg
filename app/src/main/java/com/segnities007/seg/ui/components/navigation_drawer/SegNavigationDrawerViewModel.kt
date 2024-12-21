package com.segnities007.seg.ui.components.navigation_drawer

import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SegNavigationDrawerViewModel : ViewModel() {

    private var _drawerValue = MutableStateFlow(DrawerValue.Closed)
    val drawerState: StateFlow<DrawerValue> = _drawerValue.asStateFlow()

    fun openDrawer(){
        _drawerValue.value = DrawerValue.Open
    }

    fun closeDrawer(){
        _drawerValue.value = DrawerValue.Closed
    }

}
