package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

data class SettingUiState(
    val isDatePickerDialogShow: Boolean = false,
    val newName: String = "",
    val newUserID: String = "",
)

data class SettingUiAction(
    val onLogout: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDatePickerOpen: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
)

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val authRepositoryImpl: AuthRepositoryImpl,
    ) : ViewModel() {
        var settingUiState by mutableStateOf(SettingUiState())
            private set

        fun getSettingUiAction(): SettingUiAction =
            SettingUiAction(
                onLogout = this::onLogout,
                onDatePickerClose = this::onDatePickerClose,
                onDatePickerOpen = this::onDatePickerOpen,
                onDateSelect = this::onDateSelect,
            )

        private fun onDatePickerClose() {
            settingUiState = settingUiState.copy(isDatePickerDialogShow = false)
        }

        private fun onDatePickerOpen() {
            settingUiState = settingUiState.copy(isDatePickerDialogShow = true)
        }

        private fun onLogout() {
            viewModelScope.launch(Dispatchers.IO) {
                authRepositoryImpl.logout()
            }
        }

        private fun onDateSelect(millis: Long?) {
            val instant = Instant.fromEpochMilliseconds(millis!!)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val year = localDateTime.year
            val month = localDateTime.monthNumber
            val day = localDateTime.dayOfMonth

            // TODO
        }
    }
