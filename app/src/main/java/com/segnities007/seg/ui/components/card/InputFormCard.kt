package com.segnities007.seg.ui.components.card

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
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.button.BasicButton
import com.segnities007.seg.ui.screens.login.LoginUiAction
import com.segnities007.seg.ui.screens.login.LoginUiState

@Composable
fun InputFormCard(
    modifier: Modifier = Modifier,
    padding: Dp,
    loginUiState: LoginUiState,
    loginUiAction: LoginUiAction,
    onClickSignButton: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_large)),
        contentAlignment = Alignment.Center,
    ) {
        Card {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                InputForm(
                    text = loginUiState.email,
                    label = stringResource(R.string.email),
                ) { loginUiAction.onEmailChange(it) }
                Spacer(Modifier.padding(padding))
                InputForm(
                    text = loginUiState.password,
                    label = stringResource(R.string.password),
                ) { loginUiAction.onPasswordChange(it) }
                Spacer(Modifier.padding(padding))
                Row {
                    BasicButton(
                        modifier = Modifier.weight(1f),
                        textID = R.string.sign_up,
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
