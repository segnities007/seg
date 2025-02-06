package com.segnities007.seg.ui.screens.hub.accounts

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
import javax.inject.Inject

data class AccountsUiState(
    val user: User = User(),
    val users: List<User> = listOf(),
    val isNotCompleted: Boolean = true,
)

data class AccountsUiAction(
    val onGetUser: (userID: String) -> Unit,
    val onGetUsers: (userIDs: List<String>) -> Unit,
    val onChangeIsNotCompletedOfAccounts: () -> Unit,
)

@HiltViewModel
class AccountsViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var accountsUiState by mutableStateOf(AccountsUiState())
            private set

        fun onGetAccountsUiAction(): AccountsUiAction =
            AccountsUiAction(
                onGetUsers = this::onGetUsers,
                onGetUser = this::onGetUser,
                onChangeIsNotCompletedOfAccounts = this::onChangeIsNotCompletedOfAccounts,
            )

        private fun onGetUser(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(userID)
                accountsUiState = accountsUiState.copy(user = user)
            }
        }

        private fun onGetUsers(userIDs: List<String>) {
            viewModelScope.launch(Dispatchers.IO) {
                val users = userRepository.onGetUsers(userIDs)
                accountsUiState = accountsUiState.copy(users = users)
            }
        }

        private fun onChangeIsNotCompletedOfAccounts() {
            accountsUiState = accountsUiState.copy(isNotCompleted = !accountsUiState.isNotCompleted)
        }
    }
