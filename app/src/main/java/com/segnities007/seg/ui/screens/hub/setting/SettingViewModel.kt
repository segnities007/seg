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

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val authRepositoryImpl: AuthRepositoryImpl,
    ) : ViewModel() {
        var settingState by mutableStateOf(SettingState())
            private set

        fun getSettingUiAction(): SettingAction =
            SettingAction(
                onLogout = this::onLogout,
                onDatePickerClose = this::onDatePickerClose,
                onDatePickerOpen = this::onDatePickerOpen,
                onDateSelect = this::onDateSelect,
            )

        private fun onDatePickerClose() {
            settingState = settingState.copy(isDatePickerDialogShow = false)
        }

        private fun onDatePickerOpen() {
            settingState = settingState.copy(isDatePickerDialogShow = true)
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
