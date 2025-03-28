package com.segnities007.seg.ui.screens.login.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.card.LoginCard
import com.segnities007.seg.ui.screens.login.LoginAction
import com.segnities007.seg.ui.screens.login.LoginState

@Composable
fun SignIn(
    modifier: Modifier,
    loginState: LoginState,
    loginAction: (action: LoginAction) -> Unit,
    onNavigate: () -> Unit, // go to Hub/home
) {
    LoginCard(
        modifier = modifier,
        textIDOfEnterLabel = R.string.sign_in,
        padding = dimensionResource(R.dimen.padding_normal),
        loginState = loginState,
        loginAction = loginAction,
        onClickSignButton = {
            loginAction(LoginAction.SignInWithEmailPassword(onNavigate))
        },
    )
}
