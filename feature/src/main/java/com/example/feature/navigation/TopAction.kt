package com.example.feature.navigation

import androidx.navigation.NavHostController
import com.example.domain.presentation.navigation.NavigationRoute

sealed class TopAction {
    data object OpenDrawer : TopAction()

    data object CloseDrawer : TopAction()

    data class Navigate(
        val navHostController: NavHostController,
        val route: NavigationRoute,
    ) : TopAction()
}
