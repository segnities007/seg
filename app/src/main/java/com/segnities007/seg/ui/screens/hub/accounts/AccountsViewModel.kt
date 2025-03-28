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

        fun onAccountsAction(action: AccountsAction){
            when(action){
                AccountsAction.ChangeIsNotCompletedOfAccounts -> {
                    accountsState = accountsState.copy(isNotCompleted = !accountsState.isNotCompleted)
                }
                is AccountsAction.GetUser -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val user = userRepository.onGetOtherUser(action.userID)
                        accountsState = accountsState.copy(user = user)
                    }
                }
                is AccountsAction.GetUsers -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val users = userRepository.onGetUsers(action.userIDs)
                        accountsState = accountsState.copy(users = users)
                    }
                }
            }
        }
    }
