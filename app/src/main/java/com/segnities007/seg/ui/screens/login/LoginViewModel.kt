package com.segnities007.seg.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.segnities007.seg.ui.navigation.TopLayerViewModel
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
        private val userRepository: UserRepository,
    ) : TopLayerViewModel() {
        var loginUiState by mutableStateOf(LoginState())
            private set

        private fun onConfirmEmail(onNavigate: () -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                authRepository.signInWithEmailPassword(
                    email = loginUiState.email,
                    password = loginUiState.password,
                )
                withContext(Dispatchers.Main) {
                    val isConfirmed = userRepository.onConfirmEmail()
                    if (isConfirmed) onNavigate()
                }
            }
        }

        fun onGetConfirmEmailUiAction(): ConfirmEmailUiAction =
            ConfirmEmailUiAction(
                onConfirmEmail = this::onConfirmEmail,
            )

        fun onLoginAction(action: LoginAction) {
            when (action) {
                LoginAction.ResetIsFailedSignIn -> {
                    loginUiState = loginUiState.copy(isFailedSignIn = false)
                }

                LoginAction.ChangeIsFailedSignIn -> {
                    loginUiState = loginUiState.copy(isFailedSignIn = !loginUiState.isFailedSignIn)
                }

                is LoginAction.ChangeEmail -> {
                    loginUiState = loginUiState.copy(email = action.email)
                }

                is LoginAction.ChangePassword -> {
                    loginUiState = loginUiState.copy(password = action.password)
                }

                is LoginAction.ChangeCurrentRouteName -> {
                    loginUiState = loginUiState.copy(currentRouteName = action.newCurrentRouteName)
                }

                is LoginAction.SignInWithEmailPassword -> {
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

                is LoginAction.SignUpWithEmailPassword -> {
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
        }
    }
