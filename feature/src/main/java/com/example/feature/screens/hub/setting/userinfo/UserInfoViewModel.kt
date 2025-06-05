package com.example.feature.screens.hub.setting.userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var userInfoState by mutableStateOf(UserInfoState())

        fun onUserInfoAction(action: UserInfoAction) {
            when (action) {
                is UserInfoAction.ChangeDescription,
                is UserInfoAction.ChangeName,
                is UserInfoAction.ChangeUserID,
                -> userInfoState = userInfoReducer(state = userInfoState, action = action)

                is UserInfoAction.UpdateUser -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val newUser =
                            action.user.copy(
                                name = userInfoState.name,
                                description = userInfoState.description,
                                userID = userInfoState.userID,
                                updateAt = LocalDateTime.now(),
                            )
                        userRepository.onUpdateUser(newUser)
                    }
                }
            }
        }
    }
