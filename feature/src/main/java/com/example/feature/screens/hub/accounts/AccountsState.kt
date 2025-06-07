package com.example.feature.screens.hub.accounts

import com.example.domain.model.user.User
import com.example.feature.model.UiStatus

data class AccountsState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val user: User = User(),
    val users: List<User> = listOf(),
    val isNotCompleted: Boolean = true,
)
