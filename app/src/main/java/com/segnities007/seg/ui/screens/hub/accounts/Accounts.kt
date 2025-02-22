package com.segnities007.seg.ui.screens.hub.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.AvatarCard
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.account.AccountUiAction

@Composable
fun Accounts(
    modifier: Modifier = Modifier,
    accountsViewModel: AccountsViewModel = hiltViewModel(),
    accountUiAction: AccountUiAction,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        val action = accountsViewModel.onGetAccountsUiAction()
        action.onGetUser(hubUiState.userID)
        action.onGetUsers(hubUiState.accounts)
    }

    AccountsUi(
        modifier = modifier,
        accountUiAction = accountUiAction,
        accountsUiState = accountsViewModel.accountsUiState,
        hubUiAction = hubUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun AccountsUi(
    modifier: Modifier = Modifier,
    accountUiAction: AccountUiAction,
    accountsUiState: AccountsUiState,
    hubUiAction: HubUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = accountsUiState.users.size,
            key = { index -> accountsUiState.users[index].id },
        ) {
            AvatarCard(
                user = accountsUiState.users[it],
                onCardClick = {
                    accountUiAction.onGetUserPosts(accountsUiState.users[it].userID)
                    hubUiAction.onSetUserID(accountsUiState.users[it].userID)
                    hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account.name)
                    onHubNavigate(NavigationHubRoute.Account)
                },
            )
        }
    }
}
