package com.example.feature.screens.hub.setting

fun settingReducer(
    state: SettingState,
    action: SettingAction,
): SettingState =
    when (action) {
        SettingAction.CloseDatePicker -> state.copy(isDatePickerDialogShow = false)
        SettingAction.OpenDatePicker -> state.copy(isDatePickerDialogShow = true)
        else -> state
    }
