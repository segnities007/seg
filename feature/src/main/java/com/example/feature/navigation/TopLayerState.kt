package com.example.feature.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue

data class TopLayerState(
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)
