package com.segnities007.seg.ui.screens.hub.setting.userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.segnities007.seg.data.model.User
import com.segnities007.seg.data.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

data class UserInfoUiState(
    val name: String = "",
    val userID: String = "",
    val description: String = "",
)

data class UserInfoUiAction(
    val onUserUpdate: suspend (user: User) -> Unit,
    val onDescriptionChange: (newDescription: String) -> Unit,
    val onNameChange: (newName: String) -> Unit,
    val onUserIDChange: (newUserID: String) -> Unit,
)

@HiltViewModel
class UserInfoViewModel
    @Inject
    constructor(
        private val userRepositoryImpl: UserRepositoryImpl,
    ) : ViewModel() {
        var userInfoUiState by mutableStateOf(UserInfoUiState())

        fun getUserInfoUiAction(): UserInfoUiAction =
            UserInfoUiAction(
                onUserUpdate = this::onUserUpdate,
                onUserIDChange = this::onUserIDChange,
                onNameChange = this::onNameChange,
                onDescriptionChange = this::onDescriptionChange,
            )

        private fun onNameChange(newName: String) {
            userInfoUiState = userInfoUiState.copy(name = newName)
        }

        private fun onUserIDChange(newUserID: String) {
            userInfoUiState = userInfoUiState.copy(userID = newUserID)
        }

        private fun onDescriptionChange(newDescription: String) {
            userInfoUiState = userInfoUiState.copy(description = newDescription)
        }

        private suspend fun onUserUpdate(user: User) {
            withContext(Dispatchers.IO) {
                val newUser =
                    user.copy(
                        name = userInfoUiState.name,
                        description = userInfoUiState.description,
                        userID = userInfoUiState.userID,
                        updateAt = LocalDateTime.now(),
                    )
                userRepositoryImpl.updateUser(newUser)
            }
        }
    }
