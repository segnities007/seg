package com.segnities007.seg.domain.model

import com.segnities007.seg.domain.presentation.Navigation

interface BottomBarItem {
    val selectedIconIDs: List<Int>
    val unSelectedIconIDs: List<Int>
    val routes: List<Navigation>
}
