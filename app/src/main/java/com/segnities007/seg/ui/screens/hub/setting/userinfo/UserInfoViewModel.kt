package com.segnities007.seg.ui.screens.hub.setting.userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel
    @Inject
    constructor(
        private val userRepositoryImpl: UserRepositoryImpl,
    ) : ViewModel() {
        var userInfoState by mutableStateOf(UserInfoState())

        fun onUserInfoAction(action: UserInfoAction) {
            when (action) {
                is UserInfoAction.ChangeDescription -> {
                    userInfoState = userInfoState.copy(description = action.newDescription)
                }

                is UserInfoAction.ChangeName -> {
                    userInfoState = userInfoState.copy(name = action.newName)
                }

                is UserInfoAction.ChangeUserID -> {
                    userInfoState = userInfoState.copy(userID = action.newUserID)
                }

                is UserInfoAction.UpdateUser -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val newUser =
                            action.user.copy(
                                name = userInfoState.name,
                                description = userInfoState.description,
                                userID = userInfoState.userID,
                                updateAt = LocalDateTime.now(),
                            )
                        userRepositoryImpl.onUpdateUser(newUser)
                    }
                }
            }
        }
    }
