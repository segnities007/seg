package com.example.feature.screens.hub.accounts

import com.example.domain.model.User

data class AccountsState(
    val user: User = User(),
    val users: List<User> = listOf(),
    val isNotCompleted: Boolean = true,
)
