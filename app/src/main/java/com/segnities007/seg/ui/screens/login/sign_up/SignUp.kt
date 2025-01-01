package com.segnities007.seg.ui.screens.login.sign_up

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
import com.segnities007.seg.ui.screens.login.SignUiState
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.button.BasicButton
import com.segnities007.seg.ui.screens.login.SignUiAction

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    padding: Dp = dimensionResource(R.dimen.padding_normal),
    signUiState: SignUiState,
    signUiAction: SignUiAction,
){


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        contentAlignment = Alignment.Center,
    ){
        Card {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                InputForm(
                    text = signUiState.email,
                    label = stringResource(R.string.email),
                ) { signUiAction.onEmailChange(it) }
                Spacer(Modifier.padding(padding))
                InputForm(
                    text = signUiState.password,
                    label = stringResource(R.string.password),
                ) { signUiAction.onPasswordChange(it) }
                Spacer(Modifier.padding(padding))
                Row{
                    BasicButton(
                        modifier = Modifier.weight(1f),
                        textID = R.string.enter,
                        onClick = { signUiAction.onSignUpWithEmailPassword() }
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
){

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier){
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text(label) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // フォーカスを外す
                    keyboardController?.hide() // キーボードを閉じる
                }
            )
        )
    }
}