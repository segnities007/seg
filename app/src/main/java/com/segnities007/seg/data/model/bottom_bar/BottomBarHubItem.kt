package com.segnities007.seg.data.model.bottom_bar

import androidx.compose.runtime.Immutable
import com.segnities007.seg.R
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute

@Immutable
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
