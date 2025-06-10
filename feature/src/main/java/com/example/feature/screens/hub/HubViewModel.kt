package com.example.feature.screens.hub

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.feature.model.UiStatus
import com.example.feature.navigation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HubViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : TopLayerViewModel() {
        var hubState by mutableStateOf(HubState())
            private set

        fun onHubAction(action: HubAction) {
            when (action) {
                is HubAction.GetUser -> getUser(action)

                is HubAction.SetSelf,
                is HubAction.AddPostIDToMyLikes,
                is HubAction.RemovePostIDFromMyLikes,
                is HubAction.RemovePostIDFromReposts,
                is HubAction.AddPostIDFromReposts,
                -> {
                    hubState = hubReducer(hubState, action)
                    onUpdateMyself(action)
                }

                HubAction.ResetIsHideTopBar,
                HubAction.ChangeIsHideTopBar,
                HubAction.CloseSnackBar,
                is HubAction.SetAccounts,
                is HubAction.SetComment,
                is HubAction.SetUserID,
                is HubAction.ChangeCurrentRouteName,
                is HubAction.OpenSnackBar,
                -> hubState = hubReducer(hubState, action)

                is HubAction.ReturnHubAction -> { // Nothing
                }
            }
        }

        private fun getUser(action: HubAction.GetUser) {
            hubState = hubState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val user = userRepository.onGetUser()
                    hubState =
                        hubState.copy(
                            self = user,
                            otherUserID = user.userID,
                            uiStatus = UiStatus.Success,
                        )
                } catch (e: Exception) {
                    hubState = hubState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((hubState.uiStatus as UiStatus.Error).message))
                } finally {
                    hubState = hubState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }

        private fun onUpdateMyself(action: HubAction.ReturnHubAction) {
            hubState = hubState.copy(uiStatus = UiStatus.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    userRepository.onUpdateUser(hubState.self)
                    hubState = hubState.copy(uiStatus = UiStatus.Success)
                } catch (e: Exception) {
                    hubState = hubState.copy(uiStatus = UiStatus.Error("エラーが発生しました。"))
                    action.onHubAction(HubAction.OpenSnackBar((hubState.uiStatus as UiStatus.Error).message))
                } finally {
                    hubState = hubState.copy(uiStatus = UiStatus.Initial)
                }
            }
        }
    }
