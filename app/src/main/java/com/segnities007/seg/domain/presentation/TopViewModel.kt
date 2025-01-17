package com.segnities007.seg.domain.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

data class TopState(
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)

data class TopAction(
    val onNavigate: (navHostController: NavHostController, route: Route) -> Unit,
    val openDrawer: () -> Unit,
    val closeDrawer: () -> Unit,
)

open class TopLayerViewModel : ViewModel() {
    var topState by mutableStateOf(TopState())

    fun onGetTopAction(): TopAction =
        TopAction(
            onNavigate = this::onNavigate,
            openDrawer = this::openDrawer,
            closeDrawer = this::closeDrawer,
        )

    protected fun onNavigate(
        navHostController: NavHostController,
        route: Route,
    ) {
        navHostController.navigate(route)
    }

    protected fun openDrawer() {
        topState = topState.copy(drawerState = DrawerState(DrawerValue.Open))
    }

    protected fun closeDrawer() {
        topState = topState.copy(drawerState = DrawerState(DrawerValue.Closed))
    }
}
