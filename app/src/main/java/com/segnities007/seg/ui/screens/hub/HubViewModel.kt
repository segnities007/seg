package com.segnities007.seg.ui.screens.hub

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

data class HomeUiState(
    val posts: List<Post> = listOf(),
)

data class HomeUiAction(
    val onGetNewPosts: () -> Unit,
)

data class PostUiState(
    val inputText: String = "",
    val selectedUris: List<String> = listOf(),
)

data class PostUiAction(
    val onInputTextChange: (newInputText: String) -> Unit,
    val onSelectedUrisChange: (newUris: List<String>) -> Unit,
    val onPostCreate: () -> Unit,
)

data class SettingUiState(
    val user: User = User()
)

data class SettingUiAction(
    val onUserChange: (newUser:User) -> Boolean,
    val onLogout: (navController: NavHostController) -> Unit,
)

@HiltViewModel
class HubViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val postRepositoryImpl: PostRepositoryImpl,
) : TopLayerViewModel() {

    init {
        viewModelScope.launch {
            onGetUser()
            onGetNewPosts()
        }
    }

    var user by mutableStateOf(User())
        private set

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    var navigateState by mutableStateOf(NavigateState())
        private set

    var postUiState by mutableStateOf(PostUiState())
        private set

    var settingUiState by mutableStateOf(SettingUiState())
        private set

    fun getHomeUiAction(): HomeUiAction{
        return HomeUiAction(
            onGetNewPosts = this::onGetNewPosts
        )
    }

    fun getSettingUiAction(): SettingUiAction{
        return SettingUiAction(
            onUserChange = this::onUserChange,
            onLogout = this::onLogout,
        )
    }

    fun getPostUiAction(): PostUiAction{
        return PostUiAction(
            onInputTextChange = this::onInputTextChange,
            onSelectedUrisChange = this::onSelectedUrisChange,
            onPostCreate = this::onPostCreate,
        )
    }

    fun getNavigateAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    private fun onPostCreate(){
        viewModelScope.launch {
            postRepositoryImpl.createPost(description = postUiState.inputText, user = user)
        }
    }

    private  fun onGetNewPosts(){
        viewModelScope.launch {
            homeUiState = homeUiState.copy(posts = postRepositoryImpl.getNewPosts())
        }
    }

    private suspend fun onGetUser(){
        user = userRepositoryImpl.getUser()
    }

    private fun onLogout(navController: NavHostController){
        viewModelScope.launch {
            authRepositoryImpl.logout()
            withContext(Dispatchers.Main){
                navController.navigate(Login)
            }
        }

    }

    private fun onUserChange(newUser:User): Boolean{
        settingUiState = settingUiState.copy(user = newUser)
        // TODO
        return true
    }

    private fun onSelectedUrisChange(newUris: List<String>){
        postUiState = postUiState.copy(selectedUris = newUris)
    }

    private fun onInputTextChange(newInputText: String){
        postUiState = postUiState.copy(inputText = newInputText)
    }

    private fun onIndexChange(newIndex: Int){
        navigateState = navigateState.copy(index = newIndex)
    }

}