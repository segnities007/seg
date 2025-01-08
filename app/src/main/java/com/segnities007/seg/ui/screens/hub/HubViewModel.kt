package com.segnities007.seg.ui.screens.hub

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.data.repository.UserRepositoryImpl
import com.segnities007.seg.domain.model.NavigationIndex
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class HubUiState(
    val user: User = User(),
)

data class HubUiAction(
    val onGetUser: () -> Unit,
    val onNavigate: (index: NavigationIndex) -> Unit,
)

@HiltViewModel
class HubViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
) : TopLayerViewModel() {

    init {
        onGetUser()
    }

    var hubUiState by mutableStateOf(HubUiState())
        private set

    fun getHubUiAction(): HubUiAction{
        return HubUiAction(
            onGetUser = this::onGetUser,
            onNavigate = this::onNavigate
        )
    }

    private fun onGetUser(){
        viewModelScope.launch(Dispatchers.IO){
            val user = userRepositoryImpl.getUser()
            hubUiState = hubUiState.copy(user = user)
        }
    }

}