package com.segnities007.seg.ui.components.tab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class TabUiState(
    val index: Int = 0,
    val labels: List<String> = listOf(),
)

data class TabUiAction(
    val onUpdateIndex: (newIndex: Int) -> Unit,
    val onSetLabels: (newLabels: List<String>) -> Unit,
)

class TabViewModel : ViewModel() {
    var tabUiState by mutableStateOf(TabUiState())
        private set

    fun onGetTabUiAction(): TabUiAction =
        TabUiAction(
            onUpdateIndex = this::onUpdateIndex,
            onSetLabels = this::onSetLabels,
        )

    private fun onUpdateIndex(newIndex: Int) {
        tabUiState = tabUiState.copy(index = newIndex)
    }

    private fun onSetLabels(newLabels: List<String>) {
        tabUiState = tabUiState.copy(labels = newLabels)
    }
}
