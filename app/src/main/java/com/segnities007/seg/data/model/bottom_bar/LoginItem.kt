package com.segnities007.seg.data.model.bottom_bar

import com.segnities007.seg.R

data class LoginItem(
    val labels: List<String> = listOf(
        "Sign In",
        "Sign Up",
    ),
    val selectedIconIDs: List<Int> = listOf(
        R.drawable.baseline_login_24,
        R.drawable.baseline_create_24,
    ),
    val unSelectedIconIDs: List<Int> = listOf(
        R.drawable.outline_login_24,
        R.drawable.outline_create_24,
    ),
)





