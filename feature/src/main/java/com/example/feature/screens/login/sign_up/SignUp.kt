package com.example.feature.screens.login.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationLoginRoute
import com.example.feature.R
import com.example.feature.components.card.LoginCard
import com.example.feature.screens.login.LoginAction
import com.example.feature.screens.login.LoginState

@Composable
fun SignUp(
    modifier: Modifier,
    padding: Dp = dimensionResource(R.dimen.padding_normal),
    onNavigateToConfirmEmail: (Navigation) -> Unit,
    loginState: LoginState,
    loginAction: (action: LoginAction) -> Unit,
) {
    LoginCard(
        modifier = modifier,
        textIDOfEnterLabel = R.string.sign_up,
        padding = padding,
        loginState = loginState,
        loginAction = loginAction,
        onClickSignButton = {
            loginAction(
                LoginAction.SignUpWithEmailPassword(
                    onNavigate = { onNavigateToConfirmEmail(NavigationLoginRoute.ConfirmEmail) },
                ),
            )
        },
    )
}
