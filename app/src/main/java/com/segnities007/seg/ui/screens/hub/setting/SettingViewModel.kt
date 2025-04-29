package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
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
    private val authRepository: AuthRepository,
) : ViewModel() {
    var settingState by mutableStateOf(SettingState())
        private set

    fun onSettingAction(action: SettingAction) {
        when (action) {
            SettingAction.CloseDatePicker -> {
                settingState = settingState.copy(isDatePickerDialogShow = false)
            }

            SettingAction.Logout -> {
                viewModelScope.launch(Dispatchers.IO) {
                    authRepository.logout()
                }
            }

            SettingAction.OpenDatePicker -> {
                settingState = settingState.copy(isDatePickerDialogShow = true)
            }

            is SettingAction.SelectDate -> {
                val instant = Instant.fromEpochMilliseconds(action.millis!!)
                val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                val year = localDateTime.year
                val month = localDateTime.monthNumber
                val day = localDateTime.dayOfMonth

                // TODO
            }
        }
    }
}
