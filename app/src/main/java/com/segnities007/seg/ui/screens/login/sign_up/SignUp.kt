package com.segnities007.seg.ui.screens.login.sign_up

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.segnities007.seg.ui.screens.login.SignUiState
import com.segnities007.seg.R

@Composable
fun SignUp(// TODO modify ui
    modifier: Modifier = Modifier,
    signUiState: SignUiState,
    onLoginWithGoogle: (Context) -> Unit,
    onSignUpWithEmailPassword: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
){

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){

        InputForm(text = signUiState.email, label = stringResource(R.string.email)) { onEmailChange(it) }
        InputForm(text = signUiState.password, label = stringResource(R.string.password)) { onPasswordChange(it) }
        ElevatedButton(onClick = onSignUpWithEmailPassword ){ Text(stringResource(R.string.enter)) }
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

@Composable
private fun LoginButtonWithGoogle(
    modifier: Modifier = Modifier,
    onClick: (Context) -> Unit,
    context: Context = LocalContext.current
){

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(onClick = {onClick(context)})
            //TODO modify color
            .background(color = Color.Black)
    ){
        Row {
            AsyncImage(
                model = stringResource(R.string.google_icon_url),
                contentDescription = stringResource(R.string.google_icon_url),
            )
            Text(text = stringResource(R.string.google_login_label))
        }
    }
}

