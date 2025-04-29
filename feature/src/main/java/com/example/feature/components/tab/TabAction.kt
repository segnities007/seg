package com.example.feature.components.tab

sealed class TabAction {
    data class UpdateIndex(
        val newIndex: Int,
    ) : TabAction()

    data class SetLabels(
        val newLabels: List<String>,
    ) : TabAction()
}
