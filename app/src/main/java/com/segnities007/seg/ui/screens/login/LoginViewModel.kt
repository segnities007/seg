package com.segnities007.seg.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val index: Int = 0,
)

class LoginViewModel : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword)
    }

    fun onIndexChange(newIndex: Int) {
        uiState = uiState.copy(index = newIndex)
    }

}
