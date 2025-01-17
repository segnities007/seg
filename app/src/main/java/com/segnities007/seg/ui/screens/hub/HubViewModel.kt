package com.segnities007.seg.ui.screens.hub

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.Image
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HubUiState(
    val user: User = User(),
    val userID: String = "", // of other user
    val icon: Image = Image(),
    val currentRouteName: String = "Home",
)

data class HubUiAction(
    val onGetUser: () -> Unit,
    val onGetUserID: (userID: String) -> Unit,
    val onChangeCurrentRouteName: (newRouteName: String) -> Unit,
)

@HiltViewModel
class HubViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val imageRepository: ImageRepository,
    ) : TopLayerViewModel() {
        init {
            onGetUser()
        }

        var hubUiState by mutableStateOf(HubUiState())
            private set

        fun getHubUiAction(): HubUiAction =
            HubUiAction(
                onGetUser = this::onGetUser,
                onGetUserID = this::onGetUserID,
                onChangeCurrentRouteName = this::onChangeCurrentRouteName,
            )

        private fun onGetUserID(userID: String) {
            hubUiState = hubUiState.copy(userID = userID)
        }

        private fun onGetUser() {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.getUser()
                val image = imageRepository.getImage(user.iconID)
                hubUiState = hubUiState.copy(user = user, userID = user.userID, icon = image)
            }
        }

        private fun onChangeCurrentRouteName(newRouteName: String) {
            hubUiState = hubUiState.copy(currentRouteName = newRouteName)
        }
    }
