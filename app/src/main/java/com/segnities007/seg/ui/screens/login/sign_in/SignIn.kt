package com.segnities007.seg.ui.screens.login.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.card.LoginCard
import com.segnities007.seg.ui.screens.login.LoginAction
import com.segnities007.seg.ui.screens.login.LoginState

@Composable
fun SignIn(
    modifier: Modifier,
    loginState: LoginState,
    loginAction: LoginAction,
    onNavigate: () -> Unit, // go to Hub/home
    padding: Dp = dimensionResource(R.dimen.padding_normal),
) {
    LoginCard(
        modifier = modifier,
        textIDOfEnterLabel = R.string.sign_in,
        padding = padding,
        loginState = loginState,
        loginAction = loginAction,
        onClickSignButton = {
            loginAction.onSignInWithEmailPassword(onNavigate)
        },
    )
}
