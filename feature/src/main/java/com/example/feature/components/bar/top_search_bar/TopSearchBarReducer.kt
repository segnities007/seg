package com.example.feature.components.bar.top_search_bar

internal fun topSearchBarReducer(
    state: TopSearchBarState,
    action: TopSearchBarAction,
): TopSearchBarState {
    val newState =
        when (action) {
            is TopSearchBarAction.UpdateKeyword -> {
                state.copy(keyword = action.newKeyword)
            }
        }

    return newState
}
