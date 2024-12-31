package com.segnities007.seg.domain.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class DrawerAction(
    val openDrawer: suspend () -> Unit,
    val closeDrawer: suspend () -> Unit,
)

open class TopLayerViewModel: ViewModel(){

    var drawerState by mutableStateOf(DrawerState(initialValue = DrawerValue.Closed))
        private set

    fun getDrawerAction(): DrawerAction {
        return DrawerAction(
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

