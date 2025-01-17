package com.segnities007.seg.domain.model

import com.segnities007.seg.domain.presentation.Routes

interface BottomBarItem {
    val routes: Routes
    val selectedIconIDs: List<Int>
    val unSelectedIconIDs: List<Int>
}
