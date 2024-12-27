package com.segnities007.seg.domain.model


interface BottomBarItem{
    val labels: List<String>
    val selectedIconIDs: List<Int>
    val unSelectedIconIDs: List<Int>
}