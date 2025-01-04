package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.segnities007.seg.R
import com.segnities007.seg.ui.screens.hub.AccountUiAction
import com.segnities007.seg.ui.screens.hub.AccountUiState

@Composable
fun Account(
    modifier: Modifier,
    navController: NavHostController,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
){

    when(accountUiState.index){
        0 -> Config(
            modifier = modifier,
            navController = navController,
            accountUiAction = accountUiAction,
        )
        1 -> UserInformation()
    }


}
