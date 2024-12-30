package com.segnities007.seg.ui.screens.login.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
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
import com.segnities007.seg.ui.screens.login.SignUiState
import com.segnities007.seg.R
import com.segnities007.seg.ui.screens.login.SignUiAction

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    signUiState: SignUiState,
    signUiAction: SignUiAction,
){


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ){
        ElevatedCard{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                InputForm(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
                    text = signUiState.email,
                    label = stringResource(R.string.email),
                ) { signUiAction.onEmailChange(it) }
                InputForm(
                    text = signUiState.password,
                    label = stringResource(R.string.password),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
                ) { signUiAction.onPasswordChange(it) }
                ElevatedButton(
                    onClick = { signUiAction.onSignUpWithEmailPassword() },
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
                ){ Text(stringResource(R.string.enter)) }
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