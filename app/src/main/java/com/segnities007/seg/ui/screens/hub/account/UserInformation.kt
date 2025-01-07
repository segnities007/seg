package com.segnities007.seg.ui.screens.hub.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.ui.screens.hub.AccountUiAction
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.button.BasicButton
import com.segnities007.seg.ui.screens.hub.AccountUiState

//TODO update_at auto change
//TODO add button for changing icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInformation(
    modifier: Modifier = Modifier,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal)
) {

    if(accountUiState.isDatePickerDialogShow){
        DatePickerDialog(
            onDateSelected = accountUiAction.onDateSelect,
            onDatePickerDismiss = accountUiAction.onDatePickerClose,
        )
    }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = commonPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        Spacer(modifier = Modifier.padding(commonPadding))
        TextFields(accountUiAction = accountUiAction, accountUiState = accountUiState)
        Spacer(modifier = Modifier.padding(commonPadding))

        SelectionButtons(accountUiAction = accountUiAction)
        Spacer(modifier = Modifier.padding(commonPadding))
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
private fun TextFields(
    modifier: Modifier = Modifier,
    accountUiState: AccountUiState,
    accountUiAction: AccountUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
){

    var name by remember { mutableStateOf(accountUiState.user.name) }
    var userID by remember { mutableStateOf(accountUiState.user.userID) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(){
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.new_name)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // フォーカスを外す
                    keyboardController?.hide() // キーボードを閉じる
                }
            )
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        OutlinedTextField(
            value = userID,
            onValueChange = { userID = it },
            label = { Text(stringResource(R.string.new_user_id)) },
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
private fun SelectionButtons(
    modifier: Modifier = Modifier,
    accountUiAction: AccountUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
){

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        Spacer(modifier = Modifier.padding(commonPadding))
        BasicButton(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            textID = R.string.cancel,
            onClick = { accountUiAction.onAccountIndexChange(0) },
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        BasicButton(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            textID = R.string.enter,
            onClick = {/*TODO*/}
        )
        Spacer(modifier = Modifier.padding(commonPadding))
    }

}

