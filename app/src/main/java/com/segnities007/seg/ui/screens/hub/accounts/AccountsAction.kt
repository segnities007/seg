package com.segnities007.seg.ui.screens.hub.accounts

data class AccountsAction(
    val onGetUser: (userID: String) -> Unit,
    val onGetUsers: (userIDs: List<String>) -> Unit,
    val onChangeIsNotCompletedOfAccounts: () -> Unit,
)
