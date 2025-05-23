package com.example.feature.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import com.example.feature.R
import com.example.feature.components.button.BasicButton
import com.example.feature.screens.login.LoginAction
import com.example.feature.screens.login.LoginState

@Composable
fun LoginCard(
    modifier: Modifier,
    padding: Dp,
    textIDOfEnterLabel: Int,
    loginState: LoginState,
    loginAction: (action: LoginAction) -> Unit,
    onClickSignButton: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_large)),
        contentAlignment = Alignment.Center,
    ) {
        Card {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                InputForm(
                    text = loginState.email,
                    label = stringResource(R.string.email),
                ) {
                    loginAction(LoginAction.ChangeEmail(it))
                }
                Spacer(Modifier.padding(padding))
                InputForm(
                    text = loginState.password,
                    label = stringResource(R.string.password),
                ) {
                    loginAction(LoginAction.ChangePassword(it))
                }
                Spacer(Modifier.padding(padding))
                Row {
                    BasicButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(textIDOfEnterLabel),
                        onClick = {
                            onClickSignButton()
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun InputForm(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text(label) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // フォーカスを外す
                        keyboardController?.hide() // キーボードを閉じる
                    },
                ),
        )
    }
}
