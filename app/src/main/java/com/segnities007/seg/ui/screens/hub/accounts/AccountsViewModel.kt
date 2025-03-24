package com.segnities007.seg.ui.screens.hub.accounts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var accountsState by mutableStateOf(AccountsState())
            private set

        fun onGetAccountsUiAction(): AccountsAction =
            AccountsAction(
                onGetUsers = this::onGetUsers,
                onGetUser = this::onGetUser,
                onChangeIsNotCompletedOfAccounts = this::onChangeIsNotCompletedOfAccounts,
            )

        private fun onGetUser(userID: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.onGetOtherUser(userID)
                accountsState = accountsState.copy(user = user)
            }
        }

        private fun onGetUsers(userIDs: List<String>) {
            viewModelScope.launch(Dispatchers.IO) {
                val users = userRepository.onGetUsers(userIDs)
                accountsState = accountsState.copy(users = users)
            }
        }

        private fun onChangeIsNotCompletedOfAccounts() {
            accountsState = accountsState.copy(isNotCompleted = !accountsState.isNotCompleted)
        }
    }
