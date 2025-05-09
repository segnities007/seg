package com.example.feature.screens.hub.setting.userinfo

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
import com.example.domain.presentation.navigation.Navigation
import com.example.domain.presentation.navigation.NavigationSettingRoute
import com.example.feature.R
import com.example.feature.components.button.BasicButton
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.setting.SettingAction
import com.example.feature.screens.hub.setting.SettingState
import kotlinx.coroutines.launch

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    hubState: HubState,
    settingState: SettingState,
    onHubAction: (HubAction) -> Unit,
    onSettingAction: (SettingAction) -> Unit,
    onNavigate: (Navigation) -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_normal)
    val userInfoViewModel: UserInfoViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        userInfoViewModel.onUserInfoAction(UserInfoAction.ChangeName(hubState.user.name))
        userInfoViewModel.onUserInfoAction(UserInfoAction.ChangeUserID(hubState.user.userID))
        userInfoViewModel.onUserInfoAction(UserInfoAction.ChangeDescription(hubState.user.description))
    }

    if (settingState.isDatePickerDialogShow) {
        DatePickerDialog(onSettingAction = onSettingAction)
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
        TextFields(
            userInfoState = userInfoViewModel.userInfoState,
            onUserInfoAction = userInfoViewModel::onUserInfoAction,
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        SelectionButtons(
            hubState = hubState,
            onNavigate = onNavigate,
            onUserInfoAction = userInfoViewModel::onUserInfoAction,
            onHubAction = onHubAction,
        )
        Spacer(modifier = Modifier.padding(commonPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(onSettingAction: (SettingAction) -> Unit) {
    val datePickerState: DatePickerState = rememberDatePickerState()
    DatePickerDialog(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = { onSettingAction(SettingAction.CloseDatePicker) },
        confirmButton = {
            TextButton(
                onClick = {
                    onSettingAction(SettingAction.SelectDate(datePickerState.selectedDateMillis))
                    onSettingAction(SettingAction.CloseDatePicker)
                },
            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { onSettingAction(SettingAction.CloseDatePicker) }) {
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
    onUserInfoAction: (UserInfoAction) -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_normal)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val lineHeight = 20.sp // 1行分の高さを指定（フォントサイズに応じて調整可能）
    val maxLines = 5
    val lineHeightDp = with(LocalDensity.current) { lineHeight.toDp() } // spをdpに変換

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInfoState.name,
            onValueChange = {
                onUserInfoAction(UserInfoAction.ChangeName(it))
            },
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
            onValueChange = {
                onUserInfoAction(UserInfoAction.ChangeUserID(it))
            },
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
            onValueChange = {
                onUserInfoAction(UserInfoAction.ChangeDescription(it))
            },
            label = { Text(stringResource(R.string.new_description)) },
        )
    }
}

@Composable
private fun SelectionButtons(
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onUserInfoAction: (UserInfoAction) -> Unit,
    onNavigate: (Navigation) -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_normal)
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicButton(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            text = stringResource(R.string.cancel),
            onClick = { onNavigate(NavigationSettingRoute.Preference) },
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        BasicButton(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            text = stringResource(R.string.enter),
            onClick = {
                scope.launch {
                    onUserInfoAction(UserInfoAction.UpdateUser(hubState.user))
                    onHubAction(HubAction.GetUser)
                    onNavigate(NavigationSettingRoute.Preference)
                }
            },
        )
    }
}
