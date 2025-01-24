package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    hubUiAction: HubUiAction,
    onNavigate: (Route) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (user in accountUiState.users) {
            AvatarCard(
                onCardClick = {
                    hubUiAction.onGetUserID(user.userID)
                    hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account().name)
                    onNavigate(NavigationHubRoute.Account())
                },
                user = user,
            )
        }
    }
}
