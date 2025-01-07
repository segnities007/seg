package com.segnities007.seg.ui.screens.hub

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.segnities007.seg.Login
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import com.segnities007.seg.data.repository.PostRepositoryImpl
import com.segnities007.seg.data.repository.UserRepositoryImpl
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.segnities007.seg.ui.screens.login.NavigateAction
import com.segnities007.seg.ui.screens.login.NavigateState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate

data class HubUiState(
    val user: User = User(),
)

data class HubUiAction(
    val onUserChange: (newUser: User) -> Boolean
)

@HiltViewModel
class HubViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
) : TopLayerViewModel() {

    init {
        viewModelScope.launch {
            onGetUser()
            Log.d("test", "success")
        }
    }

    var hubUiState by mutableStateOf(HubUiState())
        private set

    var navigateState by mutableStateOf(NavigateState())
        private set

    fun getNavigateAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    private suspend fun onGetUser(){
        Log.d("test", "aaaaa")
            val user = userRepositoryImpl.getUser()
        Log.d("test", "iiii")
            hubUiState = hubUiState.copy(user = user)
    }

    private fun onUserChange(newUser: User): Boolean{
        hubUiState = hubUiState.copy(user = newUser)
        // TODO
        return true
    }

    private fun onIndexChange(newIndex: Int){
        navigateState = navigateState.copy(index = newIndex)
    }

}