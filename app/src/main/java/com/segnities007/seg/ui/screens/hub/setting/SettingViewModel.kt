package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.segnities007.seg.Login
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

data class SettingUiState(
    val index: Int = 0,
    val isDatePickerDialogShow: Boolean = false,
    val newName: String = "",
    val newUserID: String = "",
)

data class SettingUiAction(
    val onLogout: (navController: NavHostController) -> Unit,
    val onIndexChange: (newIndex: Int) -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDatePickerOpen: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
): ViewModel() {

    var settingUiState by mutableStateOf(SettingUiState())
        private set

    fun getSettingUiAction(): SettingUiAction {
        return SettingUiAction(
            onLogout = this::onLogout,
            onIndexChange = this::onIndexChange,
            onDatePickerClose = this::onDatePickerClose,
            onDatePickerOpen = this::onDatePickerOpen,
            onDateSelect = this::onDateSelect,
        )
    }

    private fun onIndexChange(newIndex: Int){
        settingUiState = settingUiState.copy(index = newIndex)
    }
    private fun onDatePickerClose(){
        settingUiState = settingUiState.copy(isDatePickerDialogShow = false)
    }

    private fun onDatePickerOpen(){
        settingUiState = settingUiState.copy(isDatePickerDialogShow = true)
    }

    private fun onLogout(navController: NavHostController){
        viewModelScope.launch {
            authRepositoryImpl.logout()
            withContext(Dispatchers.Main){
                navController.navigate(Login)
            }
        }
    }

    private fun onDateSelect(millis: Long?){
        val instant = Instant.fromEpochMilliseconds(millis!!)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val year = localDateTime.year
        val month = localDateTime.monthNumber
        val day = localDateTime.dayOfMonth

        //TODO
    }
}