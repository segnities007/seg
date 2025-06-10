package com.example.feature.screens.hub.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.presentation.navigation.NavigationRoute
import com.example.domain.repository.AuthRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
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
                SettingAction.CloseDatePicker,
                SettingAction.OpenDatePicker,
                -> settingState = settingReducer(state = settingState, action = action)

                is SettingAction.Logout -> logout(action)
                is SettingAction.SelectDate -> selectDate(action)
            }
        }

        private fun logout(action: SettingAction.Logout) {
            settingState = settingState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    authRepository.logout()
                    action.onTopNavigate(NavigationRoute.Login)
                } catch (e: Exception) {
                    settingState =
                        settingState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((settingState.uiStatus as UiStatus.Error).message))
                } finally {
                    settingState = settingState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun selectDate(action: SettingAction.SelectDate) {
            val instant = Instant.fromEpochMilliseconds(action.millis!!)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val year = localDateTime.year
            val month = localDateTime.monthNumber
            val day = localDateTime.dayOfMonth

            // TODO
        }
    }
