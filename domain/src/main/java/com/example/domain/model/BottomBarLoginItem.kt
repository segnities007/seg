package com.example.domain.model

import com.example.domain.R
import com.example.domain.presentation.Navigation
import com.example.domain.presentation.NavigationLoginRoute

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
