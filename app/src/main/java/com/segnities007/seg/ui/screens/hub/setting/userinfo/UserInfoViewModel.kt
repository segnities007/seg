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

@HiltViewModel
class UserInfoViewModel
    @Inject
    constructor(
        private val userRepositoryImpl: UserRepositoryImpl,
    ) : ViewModel() {
        var userInfoState by mutableStateOf(UserInfoState())

        fun getUserInfoUiAction(): UserInfoAction =
            UserInfoAction(
                onUserUpdate = this::onUserUpdate,
                onUserIDChange = this::onUserIDChange,
                onNameChange = this::onNameChange,
                onDescriptionChange = this::onDescriptionChange,
            )

        private fun onNameChange(newName: String) {
            userInfoState = userInfoState.copy(name = newName)
        }

        private fun onUserIDChange(newUserID: String) {
            userInfoState = userInfoState.copy(userID = newUserID)
        }

        private fun onDescriptionChange(newDescription: String) {
            userInfoState = userInfoState.copy(description = newDescription)
        }

        private suspend fun onUserUpdate(user: User) {
            withContext(Dispatchers.IO) {
                val newUser =
                    user.copy(
                        name = userInfoState.name,
                        description = userInfoState.description,
                        userID = userInfoState.userID,
                        updateAt = LocalDateTime.now(),
                    )
                userRepositoryImpl.onUpdateUser(newUser)
            }
        }
    }
