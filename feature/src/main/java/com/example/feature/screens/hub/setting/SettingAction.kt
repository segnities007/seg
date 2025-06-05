package com.example.feature.screens.hub.setting

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SettingAction {
    data object Logout : SettingAction

    data object CloseDatePicker : SettingAction

    data object OpenDatePicker : SettingAction

    data class SelectDate(
        val millis: Long?,
    ) : SettingAction
}
