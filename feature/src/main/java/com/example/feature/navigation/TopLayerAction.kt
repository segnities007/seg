package com.example.feature.navigation

import androidx.navigation.NavHostController
import com.example.domain.presentation.navigation.NavigationRoute

sealed interface TopLayerAction {
    data object OpenDrawer : TopLayerAction

    data object CloseDrawer : TopLayerAction

    data class Navigate(
        val navHostController: NavHostController,
        val route: NavigationRoute,
    ) : TopLayerAction
}
