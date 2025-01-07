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
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate

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

data class AccountUiState(
    val user: User = User(),
    val index: Int = 0,
    val isDatePickerDialogShow: Boolean = false,
)

data class AccountUiAction(
    val onUserChange: (newUser:User) -> Boolean,
    val onLogout: (navController: NavHostController) -> Unit,
    val onAccountIndexChange: (newIndex: Int) -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDatePickerOpen: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
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

    var accountUiState by mutableStateOf(AccountUiState())
        private set

    fun getHomeUiAction(): HomeUiAction{
        return HomeUiAction(
            onGetNewPosts = this::onGetNewPosts
        )
    }

    fun getAccountUiAction(): AccountUiAction{
        return AccountUiAction(
            onUserChange = this::onUserChange,
            onLogout = this::onLogout,
            onAccountIndexChange = this::onAccountIndexChange,
            onDatePickerClose = this::onDatePickerClose,
            onDatePickerOpen = this::onDatePickerOpen,
            onDateSelect = this::onDateSelect,
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

    private fun onAccountIndexChange(newIndex: Int){
        accountUiState = accountUiState.copy(index = newIndex)
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
        accountUiState = accountUiState.copy(user = newUser)
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

    private fun onDatePickerClose(){
        accountUiState = accountUiState.copy(isDatePickerDialogShow = false)
    }

    private fun onDatePickerOpen(){
        accountUiState = accountUiState.copy(isDatePickerDialogShow = true)
    }

    private fun onDateSelect(millis: Long?){
        val instant = Instant.fromEpochMilliseconds(millis!!)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val year = localDateTime.year
        val month = localDateTime.monthNumber
        val day = localDateTime.dayOfMonth

        //TODO
    }
}