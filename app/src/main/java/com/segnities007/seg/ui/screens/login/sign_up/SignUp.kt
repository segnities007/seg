package com.segnities007.seg.ui.screens.login.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.card.LoginCard
import com.segnities007.seg.ui.navigation.login.NavigationLoginRoute
import com.segnities007.seg.ui.screens.login.LoginAction
import com.segnities007.seg.ui.screens.login.LoginState

@Composable
fun SignUp(
    modifier: Modifier,
    padding: Dp = dimensionResource(R.dimen.padding_normal),
    onNavigateToConfirmEmail: (Navigation) -> Unit,
    loginState: LoginState,
    loginAction: LoginAction,
) {
    LoginCard(
        modifier = modifier,
        textIDOfEnterLabel = R.string.sign_up,
        padding = padding,
        loginState = loginState,
        loginAction = loginAction,
        onClickSignButton = {
            loginAction.onSignUpWithEmailPassword {
                onNavigateToConfirmEmail(
                    NavigationLoginRoute.ConfirmEmail,
                )
            }
        },
    )
}
