package com.example.feature.components.tab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TabViewModel : ViewModel() {
    var tabUiState by mutableStateOf(TabUiState())
        private set

    fun onTabAction(action: TabAction) {
        when (action) {
            is TabAction.SetLabels -> {
                tabUiState = tabUiState.copy(labels = action.newLabels)
            }

            is TabAction.UpdateIndex -> {
                tabUiState = tabUiState.copy(index = action.newIndex)
            }
        }
    }
}
