package com.example.feature.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import com.example.feature.model.UiStatus
import com.example.feature.navigation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
                LoginAction.CloseSnackBar,
                is LoginAction.OpenSnackBar,
                is LoginAction.ChangeEmail,
                is LoginAction.ChangePassword,
                is LoginAction.ChangeCurrentRouteName,
                -> loginUiState = loginReducer(state = loginUiState, action = action)

                is LoginAction.SignInWithEmailPassword -> signInWithEmailPassword(action)
                is LoginAction.SignUpWithEmailPassword -> signUpWithEmailPassword(action)
            }
        }

        private fun signInWithEmailPassword(action: LoginAction.SignInWithEmailPassword) {
            loginUiState = loginUiState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val isSuccess =
                        authRepository.signInWithEmailPassword(
                            email = loginUiState.email,
                            password = loginUiState.password,
                        )
                    withContext(Dispatchers.Main) {
                        if (isSuccess) action.onNavigate()
                    }
                    loginUiState = loginUiState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    loginUiState =
                        loginUiState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onLoginAction(LoginAction.OpenSnackBar((loginUiState.uiStatus as UiStatus.Error).message))
                } finally {
                    loginUiState = loginUiState.copy(uiStatus = UiStatus.Success)
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
