package com.segnities007.seg.ui.screens.login.sign_up.create_account

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
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

data class CreateAccountUiState(
    val isShow: Boolean = false,
    val name: String = "",
    val userID: String = "",
    val birthday: LocalDate = LocalDate.now(),
    val path: String = "",
    val byteArray: ByteArray? = null,
)

data class CreateAccountUiAction(
    val onDatePickerOpen: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
    val onNameChange: (name: String) -> Unit,
    val onChangeUserID: (userID: String) -> Unit,
    val onBirthdayChange: (birthday: LocalDate) -> Unit,
    val onCreateUser: (onNavigateToHub: () -> Unit) -> Unit,
    val onSetPicture: (path: String, byteArray: ByteArray) -> Unit,
)

@HiltViewModel
class CreateAccountViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val tag = "CreateAccountViewModel"

        var createAccountUiState by mutableStateOf(CreateAccountUiState())
            private set

        fun onGetCreateAccountUiAction(): CreateAccountUiAction =
            CreateAccountUiAction(
                onDatePickerOpen = this::onDatePickerOpen,
                onDatePickerClose = this::onDatePickerClose,
                onDateSelect = this::onDateSelect,
                onNameChange = this::onNameChange,
                onBirthdayChange = this::onBirthdayChange,
                onCreateUser = this::onCreateUser,
                onChangeUserID = this::onChangeUserID,
                onSetPicture = this::onSetPicture,
            )

        private fun onSetPicture(
            path: String,
            byteArray: ByteArray,
        ) {
            createAccountUiState = createAccountUiState.copy(path = path, byteArray = byteArray)
        }

        private fun onChangeUserID(userID: String) {
            createAccountUiState = createAccountUiState.copy(userID = userID)
        }

        private fun onDateSelect(millis: Long?) {
            val instant = Instant.fromEpochMilliseconds(millis!!)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val year = localDateTime.year
            val month = localDateTime.monthNumber
            val day = localDateTime.dayOfMonth

            onBirthdayChange(LocalDate.of(year, month, day))
        }

        private fun onCreateUser(onNavigateToHub: () -> Unit) {
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
                            onNavigateToHub()
                        }
                    } else {
                        userRepository.onCreateUser(user)
                        withContext(Dispatchers.Main) {
                            onNavigateToHub()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(tag, "failed onCreateUser $e")
                }
            }
        }

        private fun onDatePickerOpen() {
            createAccountUiState = createAccountUiState.copy(isShow = true)
        }

        private fun onDatePickerClose() {
            createAccountUiState = createAccountUiState.copy(isShow = false)
        }

        private fun onBirthdayChange(newBirthday: LocalDate) {
            createAccountUiState = createAccountUiState.copy(birthday = newBirthday)
        }

        private fun onNameChange(newName: String) {
            createAccountUiState = createAccountUiState.copy(name = newName)
        }
    }
