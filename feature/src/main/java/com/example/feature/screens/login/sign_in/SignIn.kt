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
