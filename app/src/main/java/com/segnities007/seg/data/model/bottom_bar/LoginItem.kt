package com.segnities007.seg.data.model.bottom_bar

import com.segnities007.seg.R
import com.segnities007.seg.domain.model.BottomBarItem

data class LoginItem(
    override val labels: List<String> = listOf(
        "Sign In",
        "Sign Up",
    ),
    override val selectedIconIDs: List<Int> = listOf(
        R.drawable.baseline_login_24,
        R.drawable.baseline_create_24,
    ),
    override val unSelectedIconIDs: List<Int> = listOf(
        R.drawable.outline_login_24,
        R.drawable.outline_create_24,
    ),
): BottomBarItem





