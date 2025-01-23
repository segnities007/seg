package com.segnities007.seg.data.model.bottom_bar

import androidx.compose.runtime.Immutable
import com.segnities007.seg.R
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.NavigationHubRoute

@Immutable
data class HubItem(
    override val routes: List<Route> = NavigationHubRoute.routes,
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
) : BottomBarItem
