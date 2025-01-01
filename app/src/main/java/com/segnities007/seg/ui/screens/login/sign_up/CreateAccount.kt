package com.segnities007.seg.ui.screens.login.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.segnities007.seg.ui.screens.login.CreateAccountUiAction
import com.segnities007.seg.ui.screens.login.CreateAccountUiState
import com.segnities007.seg.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    createAccountUiState: CreateAccountUiState,
    createAccountUiAction: CreateAccountUiAction,
){

    if(createAccountUiState.isShow)
        DatePickerDialog(
            onDateSelected = createAccountUiAction.onDateSelect,
            onDatePickerDismiss = createAccountUiAction.onDatePickerClose,
        )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        InputForm(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
            text = createAccountUiState.name,
            label = stringResource(id = R.string.name),
            onValueChange = createAccountUiAction.onNameChange
        )
        ElevatedButton(
            onClick = createAccountUiAction.onDatePickerOpen,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
        ){
            Text(stringResource(id = R.string.select))
        }
        ElevatedButton(
            onClick = { createAccountUiAction.createUser(navController) },
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
        ) {
            Text(stringResource(id = R.string.enter))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState = rememberDatePickerState(),
    onDateSelected: (Long?) -> Unit,
    onDatePickerDismiss: () -> Unit,
){


    DatePickerDialog(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDatePickerDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDatePickerDismiss()
                }
            ) {
                Text(text = stringResource(R.string.ok)) }
                        },
        dismissButton = {
            TextButton(onClick = onDatePickerDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
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