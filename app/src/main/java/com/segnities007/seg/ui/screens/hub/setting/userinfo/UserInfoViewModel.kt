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
import javax.inject.Inject

data class UserInfoUiState(
    val name: String = "",
    val userID: String = "",
)

data class UserInfoUiAction(
    val onUserUpdate: suspend (user: User) -> Boolean,
    val onNameChange: (newName: String) -> Unit,
    val onUserIDChange: (newUserID: String) -> Unit,
)

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
): ViewModel() {

    var userInfoUiState by mutableStateOf(UserInfoUiState())

    fun getUserInfoUiAction(): UserInfoUiAction{
        return UserInfoUiAction(
            onUserUpdate = this::onUserUpdate,
            onUserIDChange = this::onUserIDChange,
            onNameChange = this::onNameChange,
        )
    }

    private fun onNameChange(newName: String){
        userInfoUiState = userInfoUiState.copy(name = newName)
    }

    private fun onUserIDChange(newUserID: String){
        userInfoUiState = userInfoUiState.copy(userID = newUserID)
    }

    private suspend fun onUserUpdate(user: User): Boolean{
        return withContext(Dispatchers.IO){
            val newUser = user.copy(name = userInfoUiState.name, userID = userInfoUiState.userID)
            userRepositoryImpl.updateUser(newUser)
        }
    }

}