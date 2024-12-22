package com.segnities007.seg.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


data class SignUiState(
    val email: String = "",
    val password: String = "",
)

data class NavigateUiState(
    val index: Int = 0,
)

class LoginViewModel : ViewModel() {

    var signUiState by mutableStateOf(SignUiState())
        private set
    var navigateUiState by mutableStateOf(NavigateUiState())
        private set

    fun onEmailChange(newEmail: String) {
        signUiState = signUiState.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        signUiState = signUiState.copy(password = newPassword)
    }

    fun onIndexChange(newIndex: Int) {
        navigateUiState = navigateUiState.copy(index = newIndex)
    }

}
