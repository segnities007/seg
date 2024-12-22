package com.segnities007.seg.ui.screens.login.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.segnities007.seg.ui.screens.login.SignUiState

@Composable
fun SignIn(// TODO modify ui
    modifier: Modifier = Modifier,
    signUiState: SignUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        InputForm(text = signUiState.email) { onEmailChange(it) }
        InputForm(text = signUiState.password) { onPasswordChange(it) }
    }
}

@Composable
private fun InputForm(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
){
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        label = { Text("Label") }
    )
}