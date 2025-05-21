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
                        hubState = hubState.copy(user = user, userID = user.userID)
                    }
                }

                HubAction.ChangeIsHideTopBar -> {
                    hubState = hubState.copy(isHideTopBar = !hubState.isHideTopBar)
                }

                HubAction.ResetIsHideTopBar -> {
                    hubState = hubState.copy(isHideTopBar = false)
                }

                is HubAction.SetSelf -> {
                    hubState = hubState.copy(user = action.newSelf)
                    onUpdateMyself()
                }

                is HubAction.SetUserID -> {
                    hubState = hubState.copy(userID = action.userID)
                }

                is HubAction.AddPostIDToMyLikes -> {
                    val newPosts = hubState.user.likes.plus(action.postID)
                    val updatedUser = hubState.user.copy(likes = newPosts)
                    hubState = hubState.copy(user = updatedUser)
                    onUpdateMyself()
                }

                is HubAction.ChangeCurrentRouteName -> {
                    hubState = hubState.copy(currentRouteName = action.currentRouteName)
                }

                is HubAction.RemovePostIDFromMyLikes -> {
                    val newPosts = hubState.user.likes.minus(action.postID)
                    val updatedUser = hubState.user.copy(likes = newPosts)
                    hubState = hubState.copy(user = updatedUser)
                    onUpdateMyself()
                }

                is HubAction.RemovePostIDFromReposts -> {
                    val newPosts = hubState.user.reposts.minus(action.postID)
                    val updatedUser = hubState.user.copy(reposts = newPosts)
                    hubState = hubState.copy(user = updatedUser)
                    onUpdateMyself()
                }

                is HubAction.SetAccounts -> {
                    hubState = hubState.copy(accounts = action.accounts)
                }

                is HubAction.SetComment -> {
                    hubState = hubState.copy(comment = action.comment)
                }

                is HubAction.AddPostIDFromReposts -> {
                    val newPosts = hubState.user.reposts.plus(action.postID)
                    val updatedUser = hubState.user.copy(reposts = newPosts)
                    hubState = hubState.copy(user = updatedUser)
                    onUpdateMyself()
                }
            }
        }

        private fun onUpdateMyself() {
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.onUpdateUser(hubState.user)
            }
        }
    }
