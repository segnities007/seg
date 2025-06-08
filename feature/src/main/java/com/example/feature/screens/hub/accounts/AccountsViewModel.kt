package com.example.feature.screens.hub.accounts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction
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

        fun onAccountsAction(action: AccountsAction) {
            when (action) {
                is AccountsAction.GetUser -> getUser(action)
                is AccountsAction.GetUsers -> getUsers(action)
                AccountsAction.ChangeIsNotCompletedOfAccounts,
                -> accountsState = accountsReducer(action, accountsState)
            }
        }

        private fun getUser(action: AccountsAction.GetUser) {
            accountsState = accountsState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val user = userRepository.onGetOtherUser(action.userID)
                    accountsState = accountsState.copy(user = user)
                    accountsState = accountsState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    accountsState =
                        accountsState.copy(uiStatus = UiStatus.Error("ユーザーの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((accountsState.uiStatus as UiStatus.Error).message))
                } finally {
                    accountsState = accountsState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun getUsers(action: AccountsAction.GetUsers) {
            accountsState = accountsState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val users = userRepository.onGetUsers(action.userIDs)
                    accountsState = accountsState.copy(users = users)
                    accountsState = accountsState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    accountsState =
                        accountsState.copy(uiStatus = UiStatus.Error("ユーザーの取得に失敗しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((accountsState.uiStatus as UiStatus.Error).message))
                } finally {
                    accountsState = accountsState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }
    }
