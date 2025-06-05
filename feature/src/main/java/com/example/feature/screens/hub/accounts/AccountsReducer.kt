package com.example.feature.screens.hub.accounts

fun accountsReducer(
    action: AccountsAction,
    state: AccountsState,
): AccountsState =
    when (action) {
        AccountsAction.ChangeIsNotCompletedOfAccounts -> state.copy(isNotCompleted = !state.isNotCompleted)
        else -> state
    }
