package com.segnities007.seg.data.model.bottom_bar

import androidx.compose.runtime.Immutable
import com.segnities007.seg.R
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.navigation.login.NavigationLoginRoute

@Immutable
data class BottomBarLoginItem(
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
    override val routes: List<Navigation> =
        listOf(
            NavigationLoginRoute.SignIn,
            NavigationLoginRoute.SignUp,
        ),
) : BottomBarItem
