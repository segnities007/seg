package com.example.feature.components.tab

import com.example.feature.model.UiStatus

data class TabState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val index: Int = 0,
    val labels: List<String> = listOf(),
)
