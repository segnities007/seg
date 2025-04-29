package com.example.feature.screens.hub.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.presentation.Navigation
import com.example.domain.presentation.NavigationHubRoute
import com.example.feature.components.card.AvatarCard
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.account.AccountAction

@Composable
fun Accounts(
    modifier: Modifier = Modifier,
    hubState: HubState,
    onAccountAction: (AccountAction) -> Unit,
    onHubAction: (HubAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val accountsViewModel: AccountsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        accountsViewModel.onAccountsAction(AccountsAction.GetUser(hubState.userID))
        accountsViewModel.onAccountsAction(AccountsAction.GetUsers(hubState.accounts))
    }

    AccountsUi(
        modifier = modifier,
        accountsState = accountsViewModel.accountsState,
        onHubAction = onHubAction,
        onAccountAction = onAccountAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun AccountsUi(
    modifier: Modifier = Modifier,
    accountsState: AccountsState,
    onHubAction: (HubAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
    onAccountAction: (AccountAction) -> Unit,
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
                    onAccountAction(AccountAction.GetUserPosts(accountsState.users[it].userID))
                    onHubAction(HubAction.SetUserID(accountsState.users[it].userID))
                    onHubAction(HubAction.ChangeCurrentRouteName(NavigationHubRoute.Account.name))
                    onHubNavigate(NavigationHubRoute.Account)
                },
            )
        }
    }
}
