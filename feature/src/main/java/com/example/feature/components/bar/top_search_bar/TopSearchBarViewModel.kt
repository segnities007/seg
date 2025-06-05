package com.example.feature.components.bar.top_search_bar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TopSearchBarViewModel : ViewModel() {
    var topSearchBarState by mutableStateOf(TopSearchBarState())
        private set

    fun onTopSearchBarAction(action: TopSearchBarAction) {
        when (action) {
            is TopSearchBarAction.UpdateKeyword -> {
                topSearchBarState = topSearchBarReducer(state = topSearchBarState, action = action)
            }
        }
    }
}
