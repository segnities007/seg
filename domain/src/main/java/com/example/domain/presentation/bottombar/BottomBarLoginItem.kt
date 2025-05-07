package com.example.domain.presentation.bottombar

import com.example.domain.R
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationLoginRoute

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
