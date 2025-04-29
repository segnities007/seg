package com.example.feature.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class TopLayerViewModel : ViewModel() {
    var topState by mutableStateOf(TopState())
        private set

    fun onTopAction(action: TopAction) {
        when (action) {
            TopAction.CloseDrawer -> {
                topState = topState.copy(drawerState = DrawerState(DrawerValue.Closed))
            }

            is TopAction.Navigate -> {
                action.navHostController.navigate(action.route)
            }

            TopAction.OpenDrawer -> {
                topState = topState.copy(drawerState = DrawerState(DrawerValue.Open))
            }
        }
    }
}
