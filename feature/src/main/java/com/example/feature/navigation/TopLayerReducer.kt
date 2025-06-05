package com.example.feature.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue

internal fun topLayerReducer(
    state: TopLayerState,
    action: TopLayerAction,
): TopLayerState {
    val newState =
        when (action) {
            TopLayerAction.CloseDrawer -> {
                state.copy(drawerState = DrawerState(DrawerValue.Closed))
            }

            is TopLayerAction.Navigate -> {
                action.navHostController.navigate(action.route)
                state
            }

            TopLayerAction.OpenDrawer -> {
                state.copy(drawerState = DrawerState(DrawerValue.Open))
            }
        }
    return newState
}
