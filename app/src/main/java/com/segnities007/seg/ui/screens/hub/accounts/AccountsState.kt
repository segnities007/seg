package com.segnities007.seg.ui.screens.hub.accounts

import com.segnities007.seg.data.model.User

data class AccountsState(
    val user: User = User(),
    val users: List<User> = listOf(),
    val isNotCompleted: Boolean = true,
)
