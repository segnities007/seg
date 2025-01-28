package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.AvatarCard
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun Accounts(
    modifier: Modifier = Modifier,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
    accountsUiState: AccountsUiState,
    accountsUiAction: AccountsUiAction,
    hubUiAction: HubUiAction,
    onHubNavigate: (Route) -> Unit,
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
                    accountUiAction.onGetOtherUser(accountsUiState.users[it].userID)
                    hubUiAction.onGetUserID(accountsUiState.users[it].userID)
                    hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account().name)
                    onHubNavigate(NavigationHubRoute.Account())
                },
            )
        }
//        item {
//            if(accountsUiState.users.isNotEmpty() && accountsUiState.isNotCompleted){
//                Column {
//                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_smaller)))
//                    LoadingUI(
//                        onLoading = {
//                        },
//                    )
//                }
//            }
//        }
    }
}
