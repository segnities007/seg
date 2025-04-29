package com.example.domain.model

import com.example.domain.presentation.Navigation

interface BottomBarItem {
    val selectedIconIDs: List<Int>
    val unSelectedIconIDs: List<Int>
    val routes: List<Navigation>
}
