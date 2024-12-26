package com.segnities007.seg.ui.components.navigation_drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SegNavigationDrawerViewModel : ViewModel() {

    var drawerState by mutableStateOf(DrawerState(initialValue = DrawerValue.Closed))
        private set

    fun openDrawer(){
        viewModelScope.launch{
            drawerState.open()
        }
    }

    fun closeDrawer(){
        viewModelScope.launch{
            drawerState.close()
        }
    }

}
