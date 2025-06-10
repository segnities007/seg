package com.example.feature.screens.login.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.feature.R
import com.example.feature.components.card.LoginCard
import com.example.feature.screens.login.LoginAction
import com.example.feature.screens.login.LoginState

@Composable
fun SignIn(
    modifier: Modifier,
    loginState: LoginState,
    onNavigate: () -> Unit, // go to Hub/home
    onLoginAction: (LoginAction) -> Unit,
) {
    LoginCard(
        modifier = modifier,
        textIDOfEnterLabel = R.string.sign_in,
        padding = dimensionResource(R.dimen.padding_normal),
        loginState = loginState,
        loginAction = onLoginAction,
        onClickSignButton = {
            onLoginAction(LoginAction.SignInWithEmailPassword(onNavigate, onLoginAction))
        },
    )
}
