package com.example.feature.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class TopLayerViewModel : ViewModel() {
    var topLayerState by mutableStateOf(TopLayerState())
        private set

    fun onTopAction(action: TopLayerAction) {
        when (action) {
            TopLayerAction.CloseDrawer,
            is TopLayerAction.Navigate,
            TopLayerAction.OpenDrawer,
            ->
                topLayerState =
                    topLayerReducer(state = topLayerState, action = action)
        }
    }
}
