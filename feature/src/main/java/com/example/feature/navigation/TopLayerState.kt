package com.example.feature.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import com.example.feature.model.UiStatus

data class TopLayerState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)
