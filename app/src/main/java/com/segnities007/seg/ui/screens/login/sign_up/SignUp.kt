package com.segnities007.seg.ui.screens.login.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.card.InputFormCard
import com.segnities007.seg.ui.navigation.login.NavigationLoginRoute
import com.segnities007.seg.ui.screens.login.LoginUiAction
import com.segnities007.seg.ui.screens.login.LoginUiState

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    padding: Dp = dimensionResource(R.dimen.padding_normal),
    onNavigateToConfirmEmail: (Route) -> Unit,
    loginUiState: LoginUiState,
    loginUiAction: LoginUiAction,
) {
    InputFormCard(
        modifier = modifier,
        padding = padding,
        loginUiState = loginUiState,
        loginUiAction = loginUiAction,
        onClickSignButton = {
            loginUiAction.onSignUpWithEmailPassword {
                onNavigateToConfirmEmail(
                    NavigationLoginRoute.ConfirmEmail(),
                )
            }
        },
    )
}
