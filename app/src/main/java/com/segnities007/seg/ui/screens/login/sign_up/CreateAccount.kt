package com.segnities007.seg.ui.screens.login.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.segnities007.seg.ui.screens.login.CreateAccountUiAction
import com.segnities007.seg.ui.screens.login.CreateAccountUiState


//TODO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    createAccountUiState: CreateAccountUiState,
    createAccountUiAction: CreateAccountUiAction,
){

    if(createAccountUiState.isShow)
        DatePickerDialog(
            onDateSelected = {/*TODO*/},
            onDatePickerDismiss = createAccountUiAction.onDatePickerClose,
        )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        InputForm()
        ElevatedButton(onClick = createAccountUiAction.onDatePickerOpen){
            Text("Select")
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

        Box(
            modifier = Modifier.fillMaxSize()
        ){
            DatePickerDialog(
                onDismissRequest = onDatePickerDismiss,
                confirmButton = {
                    TextButton(onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDatePickerDismiss) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

}

@Composable
private fun InputForm(){

}