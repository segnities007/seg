package com.segnities007.seg.ui.navigation

import androidx.navigation.NavHostController

sealed class TopAction {
    data object OpenDrawer : TopAction()

    data object CloseDrawer : TopAction()

    data class Navigate(
        val navHostController: NavHostController,
        val route: NavigationRoute,
    ) : TopAction()
}
