package com.example.feature.screens.hub.accounts

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AccountsAction {
    data object ChangeIsNotCompletedOfAccounts : AccountsAction

    data class GetUser(
        val userID: String,
    ) : AccountsAction

    data class GetUsers(
        val userIDs: List<String>,
    ) : AccountsAction
}
