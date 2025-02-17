package com.segnities007.seg.ui.components.bar.status_bar

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.data.model.User

@Stable
interface StatusBarScope {
    val user: User
    val commonPadding: Dp
}

data class DefaultStatusBarScope(
    override val user: User,
    override val commonPadding: Dp,
) : StatusBarScope
