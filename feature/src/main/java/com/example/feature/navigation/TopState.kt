package com.example.feature.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue

data class TopState(
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)
