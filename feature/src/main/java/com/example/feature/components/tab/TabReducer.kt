package com.example.feature.components.tab

internal fun tabReducer(
    state: TabState,
    action: TabAction,
): TabState =
    when (action) {
        is TabAction.SetLabels,
        -> state.copy(labels = action.newLabels)

        is TabAction.UpdateIndex,
        -> state.copy(index = action.newIndex)
    }
