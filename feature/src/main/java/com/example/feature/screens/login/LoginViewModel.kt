package com.example.feature.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import com.example.feature.navigation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ConfirmEmailUiAction(
    val onConfirmEmail: (onNavigate: () -> Unit) -> Unit,
)

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : TopLayerViewModel() {
        var loginUiState by mutableStateOf(LoginState())
            private set

        fun onLoginAction(action: LoginAction) {
            when (action) {
                LoginAction.ResetIsFailedSignIn,
                LoginAction.ChangeIsFailedSignIn,
                is LoginAction.ChangeEmail,
                is LoginAction.ChangePassword,
                is LoginAction.ChangeCurrentRouteName,
                -> loginUiState = loginReducer(state = loginUiState, action = action)

                is LoginAction.SignInWithEmailPassword -> signInWithEmailPassword(action)
                is LoginAction.SignUpWithEmailPassword -> signUpWithEmailPassword(action)
            }
        }

        private fun signInWithEmailPassword(action: LoginAction.SignInWithEmailPassword) {
            viewModelScope.launch(Dispatchers.IO) {
                val isSuccess =
                    authRepository.signInWithEmailPassword(
                        email = loginUiState.email,
                        password = loginUiState.password,
                    )
                withContext(Dispatchers.Main) {
                    if (isSuccess) action.onNavigate()
                }
            }
        }

        private fun signUpWithEmailPassword(action: LoginAction.SignUpWithEmailPassword) {
            viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    val isSuccess =
                        authRepository.signUpWithEmailPassword(
                            email = loginUiState.email,
                            password = loginUiState.password,
                        )
                    if (isSuccess) action.onNavigate()
                }
            }
        }
    }
