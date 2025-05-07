package com.example.domain.presentation.bottombar

import com.example.domain.presentation.navigation.Navigation

interface BottomBarItem {
    val selectedIconIDs: List<Int>
    val unSelectedIconIDs: List<Int>
    val routes: List<Navigation>
}
