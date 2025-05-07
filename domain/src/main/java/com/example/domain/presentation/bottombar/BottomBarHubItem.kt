package com.example.domain.presentation.bottombar

import com.example.domain.R
import com.example.domain.presentation.bottombar.BottomBarItem
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationHubRoute

data class BottomBarHubItem(
    override val selectedIconIDs: List<Int> =
        listOf(
            R.drawable.baseline_home_24,
            R.drawable.baseline_trending_up_24,
            R.drawable.baseline_post_add_24,
            R.drawable.baseline_notifications_24,
            R.drawable.baseline_settings_24,
        ),
    override val unSelectedIconIDs: List<Int> =
        listOf(
            R.drawable.outline_home_24,
            R.drawable.outline_trending_up_24,
            R.drawable.outline_post_add_24,
            R.drawable.outline_notifications_24,
            R.drawable.outline_settings_24,
        ),
    override val routes: List<Navigation> =
        listOf(
            NavigationHubRoute.Home,
            NavigationHubRoute.Trend,
            NavigationHubRoute.Post,
            NavigationHubRoute.Notify,
            NavigationHubRoute.Setting,
        ),
) : BottomBarItem
