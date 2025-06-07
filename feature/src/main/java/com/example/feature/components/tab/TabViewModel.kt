package com.example.feature.components.tab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TabViewModel : ViewModel() {
    var tabState by mutableStateOf(TabState())
        private set

    fun onTabAction(action: TabAction) {
        when (action) {
            is TabAction.SetLabels,
            is TabAction.UpdateIndex,
            -> tabState = tabReducer(state = tabState, action = action)
        }
    }
}
