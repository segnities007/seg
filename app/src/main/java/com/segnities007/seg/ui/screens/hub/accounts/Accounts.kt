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
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.account.AccountAction

@Composable
fun Accounts(
    modifier: Modifier = Modifier,
    accountsViewModel: AccountsViewModel = hiltViewModel(),
    accountAction: AccountAction,
    hubState: HubState,
    hubAction: HubAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        val action = accountsViewModel.onGetAccountsUiAction()
        action.onGetUser(hubState.userID)
        action.onGetUsers(hubState.accounts)
    }

    AccountsUi(
        modifier = modifier,
        accountAction = accountAction,
        accountsState = accountsViewModel.accountsState,
        hubAction = hubAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun AccountsUi(
    modifier: Modifier = Modifier,
    accountAction: AccountAction,
    accountsState: AccountsState,
    hubAction: HubAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = accountsState.users.size,
            key = { index -> accountsState.users[index].id },
        ) {
            AvatarCard(
                user = accountsState.users[it],
                onCardClick = {
                    accountAction.onGetUserPosts(accountsState.users[it].userID)
                    hubAction.onSetUserID(accountsState.users[it].userID)
                    hubAction.onChangeCurrentRouteName(NavigationHubRoute.Account.name)
                    onHubNavigate(NavigationHubRoute.Account)
                },
            )
        }
    }
}
