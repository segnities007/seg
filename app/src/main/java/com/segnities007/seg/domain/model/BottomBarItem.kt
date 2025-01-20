package com.segnities007.seg.domain.model

import com.segnities007.seg.domain.presentation.Route

interface BottomBarItem {
    val routes: List<Route>
    val selectedIconIDs: List<Int>
    val unSelectedIconIDs: List<Int>
}
