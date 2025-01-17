package com.segnities007.seg.data.model.bottom_bar

import com.segnities007.seg.R
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.Routes
import com.segnities007.seg.navigation.login.NavigationLoginRoute

data class LoginItem(
    override val routes: Routes = NavigationLoginRoute,
    override val selectedIconIDs: List<Int> =
        listOf(
            R.drawable.baseline_login_24,
            R.drawable.baseline_create_24,
        ),
    override val unSelectedIconIDs: List<Int> =
        listOf(
            R.drawable.outline_login_24,
            R.drawable.outline_create_24,
        ),
) : BottomBarItem
