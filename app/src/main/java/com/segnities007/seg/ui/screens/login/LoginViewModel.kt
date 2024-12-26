package com.segnities007.seg.ui.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SignUiState(
    val email: String = "",
    val password: String = "",
)

data class NavigateUiState(
    val index: Int = 0,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

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

    fun onLoginWithGoogle(
        context: Context,
    ){
        viewModelScope.launch(Dispatchers.IO){
            authRepositoryImpl.loginWithGoogle(context = context)
        }
    }

}
