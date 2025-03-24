package com.segnities007.seg.ui.screens.hub.setting.userinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.button.BasicButton
import com.segnities007.seg.ui.navigation.hub.setting.NavigationSettingRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.setting.SettingAction
import com.segnities007.seg.ui.screens.hub.setting.SettingState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    hubState: HubState,
    hubAction: HubAction,
    settingState: SettingState,
    settingAction: SettingAction,
    userInfoViewModel: UserInfoViewModel = hiltViewModel(),
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    onNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        val action = userInfoViewModel.getUserInfoUiAction()
        action.onNameChange(hubState.user.name)
        action.onUserIDChange(hubState.user.userID)
        action.onDescriptionChange(hubState.user.description)
    }

    if (settingState.isDatePickerDialogShow) {
        DatePickerDialog(
            onDateSelected = settingAction.onDateSelect,
            onDatePickerDismiss = settingAction.onDatePickerClose,
        )
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_large))
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextFields(userInfoState = userInfoViewModel.userInfoState, userInfoAction = userInfoViewModel.getUserInfoUiAction())
        Spacer(modifier = Modifier.padding(commonPadding))
        SelectionButtons(
            onNavigate = onNavigate,
            userInfoAction = userInfoViewModel.getUserInfoUiAction(),
            hubState = hubState,
            hubAction = hubAction,
        )
        Spacer(modifier = Modifier.padding(commonPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    datePickerState: DatePickerState = rememberDatePickerState(),
    onDateSelected: (Long?) -> Unit,
    onDatePickerDismiss: () -> Unit,
) {
    DatePickerDialog(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDatePickerDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDatePickerDismiss()
                },
            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDatePickerDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun TextFields(
    userInfoState: UserInfoState,
    userInfoAction: UserInfoAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val lineHeight = 20.sp // 1行分の高さを指定（フォントサイズに応じて調整可能）
    val maxLines = 5
    val lineHeightDp = with(LocalDensity.current) { lineHeight.toDp() } // spをdpに変換

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInfoState.name,
            onValueChange = { userInfoAction.onNameChange(it) },
            label = { Text(stringResource(R.string.new_name)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // フォーカスを外す
                        keyboardController?.hide() // キーボードを閉じる
                    },
                ),
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInfoState.userID,
            onValueChange = { userInfoAction.onUserIDChange(it) },
            label = { Text(stringResource(R.string.new_user_id)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // フォーカスを外す
                        keyboardController?.hide() // キーボードを閉じる
                    },
                ),
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        OutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = lineHeightDp * maxLines),
            // 高さを`maxLines`に合わせる
            maxLines = maxLines,
            value = userInfoState.description,
            onValueChange = { userInfoAction.onDescriptionChange(it) },
            label = { Text(stringResource(R.string.new_description)) },
        )
    }
}

@Composable
private fun SelectionButtons(
    hubState: HubState,
    hubAction: HubAction,
    userInfoAction: UserInfoAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    onNavigate: (Navigation) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicButton(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            textID = R.string.cancel,
            onClick = { onNavigate(NavigationSettingRoute.Preference) },
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        BasicButton(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            textID = R.string.enter,
            onClick = {
                scope.launch {
                    userInfoAction.onUserUpdate(hubState.user)
                    hubAction.onGetUser()
                    onNavigate(NavigationSettingRoute.Preference)
                }
            },
        )
    }
}
