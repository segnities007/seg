package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.ui.screens.hub.account.config.Config
import com.segnities007.seg.ui.screens.hub.account.userinfo.UserInformation

@Composable
fun Account(
    modifier: Modifier,
    navController: NavHostController,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    accountViewModel: AccountViewModel = hiltViewModel(),
){

    when(accountViewModel.accountUiState.index){// TODO change index to enum
        0 -> Config(
            modifier = modifier,
            navController = navController,
            accountUiAction = accountViewModel.getAccountUiAction(),
        )
        1 -> UserInformation(
            modifier = modifier,
            accountUiState = accountViewModel.accountUiState,
            accountUiAction = accountViewModel.getAccountUiAction(),
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
        )
    }


}
