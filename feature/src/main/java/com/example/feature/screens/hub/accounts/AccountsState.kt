package com.example.feature.screens.hub.accounts

import com.example.domain.model.user.User

data class AccountsState(
    val user: User = User(),
    val users: List<User> = listOf(),
    val isNotCompleted: Boolean = true,
)
