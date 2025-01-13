package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.ui.components.card.AvatarCard
import com.segnities007.seg.ui.components.top_bar.TopStatusBar
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Accounts(
    modifier: Modifier = Modifier,
    accountViewModel: AccountViewModel = hiltViewModel(),
){

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        for (user in accountViewModel.accountUiState.users){

            LaunchedEffect(Unit) {
                accountViewModel.getAccountUiAction().onGetIcon(user.iconID)
            }

            AvatarCard(
                onCardClick = {},
                url = accountViewModel.accountUiState.icon.imageUrl,
                user = user,
            )
        }
    }
}