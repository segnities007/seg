package com.example.feature.screens.login.sign_up.create_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.user.User
import com.example.domain.repository.UserRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.login.LoginAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var createAccountUiState by mutableStateOf(CreateAccountState())
            private set

        fun onCreateAccountAction(action: CreateAccountAction) {
            when (action) {
                CreateAccountAction.CloseDatePicker,
                CreateAccountAction.OpenDatePicker,
                is CreateAccountAction.ChangeName,
                is CreateAccountAction.ChangeUserID,
                is CreateAccountAction.ChangeBirthday,
                is CreateAccountAction.SetUri,
                is CreateAccountAction.SetPicture,
                ->
                    createAccountUiState =
                        createAccountReducer(state = createAccountUiState, action = action)

                is CreateAccountAction.CreateUser -> createUser(action)
                is CreateAccountAction.SetDate -> setDate(action)
            }
        }

        private fun createUser(action: CreateAccountAction.CreateUser) {
            createAccountUiState = createAccountUiState.copy(uiStatus = UiStatus.Loading)
            val user =
                User(
                    id = "",
                    userID = createAccountUiState.userID,
                    name = createAccountUiState.name,
                    birthday = createAccountUiState.birthday,
                )
            val updatedPath = createAccountUiState.path.substringAfterLast("/") + ".png"
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (createAccountUiState.byteArray != null) {
                        userRepository.onCreateUserWithIcon(
                            user = user,
                            path = updatedPath,
                            byteArray = createAccountUiState.byteArray!!,
                        )
                        withContext(Dispatchers.Main) {
                            action.onNavigateToHub()
                        }
                    } else {
                        userRepository.onCreateUser(user)
                        withContext(Dispatchers.Main) {
                            action.onNavigateToHub()
                        }
                    }
                    createAccountUiState = createAccountUiState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    createAccountUiState =
                        createAccountUiState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onLoginAction(LoginAction.OpenSnackBar((createAccountUiState.uiStatus as UiStatus.Error).message))
                } finally {
                    createAccountUiState = createAccountUiState.copy(uiStatus = UiStatus.Success)
                }
            }
        }

        private fun setDate(action: CreateAccountAction.SetDate) {
            val instant = Instant.fromEpochMilliseconds(action.millis!!)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val year = localDateTime.year
            val month = localDateTime.monthNumber
            val day = localDateTime.dayOfMonth
            createAccountUiState =
                createAccountUiState.copy(birthday = LocalDate.of(year, month, day))
        }
    }
