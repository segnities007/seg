package com.segnities007.seg.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.AuthRepository
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import javax.inject.Inject

// class for SignIn and SignUp UI

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val currentRouteName: String = "SignIn",
    val isFailedSignIn: Boolean = false,
)

data class LoginUiAction(
    val onChangeIsFailedSignIn: () -> Unit,
    val onResetIsFailedSignIn: () -> Unit,
    val onChangeCurrentRouteName: (newCurrentRouteName: String) -> Unit,
    val onPasswordChange: (password: String) -> Unit,
    val onEmailChange: (email: String) -> Unit,
    val onSignUpWithEmailPassword: (onNavigate: () -> Unit) -> Unit,
    val onSignInWithEmailPassword: (onNavigate: () -> Unit) -> Unit,
)

// class for ConfirmEmail UI

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
        var loginUiState by mutableStateOf(LoginUiState())
            private set

        fun onGetConfirmEmailUiAction(): ConfirmEmailUiAction =
            ConfirmEmailUiAction(
                onConfirmEmail = this::onConfirmEmail,
            )

        fun onGetLoginAction(): LoginUiAction =
            LoginUiAction(
                onChangeIsFailedSignIn = this::onChangeIsFailedSignIn,
                onResetIsFailedSignIn = this::onResetIsFailedSignIn,
                onChangeCurrentRouteName = this::onChangeCurrentRouteName,
                onEmailChange = this::onEmailChange,
                onPasswordChange = this::onPasswordChange,
                onSignUpWithEmailPassword = this::onSignUpWithEmailPassword,
                onSignInWithEmailPassword = this::onSignInWithEmailPassword,
            )

        private fun onChangeCurrentRouteName(newCurrentRouteName: String) {
            loginUiState = loginUiState.copy(currentRouteName = newCurrentRouteName)
        }

        private fun onChangeIsFailedSignIn() {
            loginUiState = loginUiState.copy(isFailedSignIn = !loginUiState.isFailedSignIn)
        }

        private fun onResetIsFailedSignIn() {
            loginUiState = loginUiState.copy(isFailedSignIn = false)
        }

        private fun onConfirmEmail(onNavigate: () -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                authRepository.signInWithEmailPassword(
                    email = loginUiState.email,
                    password = loginUiState.password,
                )
                withContext(Dispatchers.Main) {
                    val isConfirmed = userRepository.confirmEmail()
                    if (isConfirmed) onNavigate()
                }
            }
        }

        private fun onEmailChange(newEmail: String) {
            loginUiState = loginUiState.copy(email = newEmail)
        }

        private fun onPasswordChange(newPassword: String) {
            loginUiState = loginUiState.copy(password = newPassword)
        }

        private fun onSignUpWithEmailPassword(onNavigate: () -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    val isSuccess =
                        authRepository.signUpWithEmailPassword(
                            email = loginUiState.email,
                            password = loginUiState.password,
                        )
                    if (isSuccess) onNavigate()
                }
            }
        }

        private fun onSignInWithEmailPassword(onNavigate: () -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                val isSuccess =
                    authRepository.signInWithEmailPassword(
                        email = loginUiState.email,
                        password = loginUiState.password,
                    )
                withContext(Dispatchers.Main) {
                    if (isSuccess) onNavigate()
                }
            }
        }
    }
