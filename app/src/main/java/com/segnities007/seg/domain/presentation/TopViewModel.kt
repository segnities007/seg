package com.segnities007.seg.domain.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.segnities007.seg.domain.model.NavigationIndex

data class TopState(
    val index: NavigationIndex = NavigationIndex.No,
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)

data class TopAction(
    val onNavigate: (index: NavigationIndex) -> Unit,
    val openDrawer: () -> Unit,
    val closeDrawer: () -> Unit,
)

open class TopLayerViewModel: ViewModel(){

    var topState by mutableStateOf(TopState())

    fun getTopAction(): TopAction {
        return TopAction(
            openDrawer = this::openDrawer,
            closeDrawer = this::closeDrawer,
            onNavigate = this::onNavigate,
        )
    }

    protected fun onNavigate(index: NavigationIndex){
        topState = topState.copy(index = index)
    }

    protected fun openDrawer(){
        topState = topState.copy(drawerState = DrawerState(DrawerValue.Open))
    }

    protected fun closeDrawer(){
        topState = topState.copy(drawerState = DrawerState(DrawerValue.Closed))
    }

}

