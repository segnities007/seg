package com.example.feature.screens.hub.setting

import androidx.compose.runtime.Immutable
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.screens.hub.HubAction

@Immutable
sealed interface SettingAction {
    data class Logout(
        val onTopNavigate: (Navigation) -> Unit,
        val onHubAction: (HubAction) -> Unit,
    ) : SettingAction

    data object CloseDatePicker : SettingAction

    data object OpenDatePicker : SettingAction

    data class SelectDate(
        val millis: Long?,
    ) : SettingAction
}
