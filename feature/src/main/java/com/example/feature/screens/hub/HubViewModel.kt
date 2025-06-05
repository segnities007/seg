package com.example.feature.screens.hub

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
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
                HubAction.GetUser -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val user = userRepository.onGetUser()
                        hubState = hubState.copy(self = user, otherUserID = user.userID)
                    }
                }

                is HubAction.SetSelf,
                is HubAction.AddPostIDToMyLikes,
                is HubAction.RemovePostIDFromMyLikes,
                is HubAction.RemovePostIDFromReposts,
                is HubAction.AddPostIDFromReposts,
                -> {
                    hubState = hubReducer(hubState, action)
                    onUpdateMyself()
                }

                HubAction.ResetIsHideTopBar,
                HubAction.ChangeIsHideTopBar,
                is HubAction.SetAccounts,
                is HubAction.SetComment,
                is HubAction.SetUserID,
                is HubAction.ChangeCurrentRouteName,
                -> hubState = hubReducer(hubState, action)
            }
        }

        private fun onUpdateMyself() {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.onUpdateUser(hubState.self)
            }
        }
    }
