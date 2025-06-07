package com.example.feature.screens.hub.setting

import com.example.feature.model.UiStatus

data class SettingState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val isDatePickerDialogShow: Boolean = false,
    val newName: String = "",
    val newUserID: String = "",
)
