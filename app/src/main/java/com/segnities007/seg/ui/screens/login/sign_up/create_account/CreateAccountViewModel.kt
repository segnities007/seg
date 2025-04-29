package com.segnities007.seg.ui.screens.login.sign_up.create_account

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
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
    private val tag = "CreateAccountViewModel"

    var createAccountUiState by mutableStateOf(CreateAccountState())
        private set

    fun onCreateAccountAction(action: CreateAccountAction) {
        when (action) {
            CreateAccountAction.OpenDatePicker -> {
                createAccountUiState = createAccountUiState.copy(isShow = true)
            }

            CreateAccountAction.CloseDatePicker -> {
                createAccountUiState = createAccountUiState.copy(isShow = false)
            }

            is CreateAccountAction.CreateUser -> {
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
                    } catch (e: Exception) {
                        Log.e(tag, "failed onCreateUser $e")
                    }
                }
            }

            is CreateAccountAction.ChangeBirthday -> {
                onBirthdayChange(action.birthday)
            }

            is CreateAccountAction.ChangeName -> {
                createAccountUiState = createAccountUiState.copy(name = action.name)
            }

            is CreateAccountAction.ChangeUserID -> {
                createAccountUiState = createAccountUiState.copy(userID = action.userID)
            }

            is CreateAccountAction.SetDate -> {
                val instant = Instant.fromEpochMilliseconds(action.millis!!)
                val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                val year = localDateTime.year
                val month = localDateTime.monthNumber
                val day = localDateTime.dayOfMonth

                onBirthdayChange(LocalDate.of(year, month, day))
            }

            is CreateAccountAction.SetUri -> {
                createAccountUiState = createAccountUiState.copy(uri = action.uri)
            }

            is CreateAccountAction.SetPicture -> {
                createAccountUiState =
                    createAccountUiState.copy(path = action.path, byteArray = action.byteArray)
            }
        }
    }

    private fun onBirthdayChange(newBirthday: LocalDate) {
        createAccountUiState = createAccountUiState.copy(birthday = newBirthday)
    }
}
