package com.example.feature.screens.hub.accounts

import androidx.compose.runtime.Immutable
import com.example.feature.screens.hub.HubAction

@Immutable
sealed interface AccountsAction {
    data object ChangeIsNotCompletedOfAccounts : AccountsAction

    data class GetUser(
        val userID: String,
        val onHubAction: (HubAction) -> Unit,
    ) : AccountsAction

    data class GetUsers(
        val userIDs: List<String>,
        val onHubAction: (HubAction) -> Unit,
    ) : AccountsAction
}
