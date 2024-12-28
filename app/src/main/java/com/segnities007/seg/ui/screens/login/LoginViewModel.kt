package com.segnities007.seg.ui.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.segnities007.seg.Hub
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import javax.inject.Inject

// class for SignIn and SignUp UI

data class SignUiState(
    val email: String = "",
    val password: String = "",
)

data class SignUiAction(
    val onPasswordChange: (String) -> Unit,
    val onEmailChange: (String) -> Unit,
    val onLoginWithGoogle: (Context) -> Unit,
    val onSignUpWithEmailPassword: (NavHostController) -> Unit,
    val onSignInWithEmailPassword: (NavHostController) -> Unit,
)


// class for navigation

data class NavigateState(
    val index: Int = 0,
)

data class NavigateAction(
    val onIndexChange: (Int) -> Unit,
)


// class for CreateAccount UI

data class CreateAccountUiState(
    val isShow: Boolean = false,
    val name: String = "",
    val birthday: LocalDate = LocalDate(2020, 1, 1),
)

data class CreateAccountUiAction(
    val onDatePickerOpen: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onNameChange: (String) -> Unit,
    val onBirthdayChange: (LocalDate) -> Unit,
)

//


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    var signUiState by mutableStateOf(SignUiState())
        private set
    var navigateUiState by mutableStateOf(NavigateState())
        private set
    var createAccountUiState by mutableStateOf(CreateAccountUiState())
        private set

    fun getNavigateAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    fun getSignUiAction(): SignUiAction{
        return SignUiAction(
            onEmailChange = this::onEmailChange,
            onPasswordChange = this::onPasswordChange,
            onLoginWithGoogle = this::onLoginWithGoogle,
            onSignUpWithEmailPassword = this::onSignUpWithEmailPassword,
            onSignInWithEmailPassword = this::onSignInWithEmailPassword,
        )
    }

    fun getNavigationUiAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    fun getCreateAccountUiAction(): CreateAccountUiAction{
        return CreateAccountUiAction(
            onDatePickerOpen = this::onDatePickerOpen,
            onDatePickerClose = this::onDatePickerClose,
            onNameChange = this::onNameChange,
            onBirthdayChange = this::onBirthdayChange,
        )
    }

    private fun onDatePickerOpen(){
        createAccountUiState = createAccountUiState.copy(isShow = true)
    }

    private fun onDatePickerClose(){
        createAccountUiState = createAccountUiState.copy(isShow = false)
    }

    private fun onBirthdayChange(newBirthday: LocalDate){
        createAccountUiState = createAccountUiState.copy(birthday = newBirthday)
    }

    private fun onNameChange(newName: String){
        createAccountUiState = createAccountUiState.copy(name = newName)
    }

    private fun onEmailChange(newEmail: String) {
        signUiState = signUiState.copy(email = newEmail)
    }

    private fun onPasswordChange(newPassword: String) {
        signUiState = signUiState.copy(password = newPassword)
    }

    private fun onIndexChange(newIndex: Int) {
        navigateUiState = navigateUiState.copy(index = newIndex)
    }

    private fun onLoginWithGoogle(
        context: Context,
    ){
        viewModelScope.launch(Dispatchers.IO){
            authRepositoryImpl.loginWithGoogle(context = context)
        }
    }

    private fun onSignUpWithEmailPassword(
        navController: NavHostController,
    ){
        viewModelScope.launch(Dispatchers.IO){
            val isSuccess = authRepositoryImpl
                .signUpWithEmailPassword(
                    email = signUiState.email,
                    password = signUiState.password
                )
            withContext(Dispatchers.Main){
                if(isSuccess) navController.navigate(route = Hub)
            }
        }
    }

    private fun onSignInWithEmailPassword(
        navController: NavHostController,
    ){
        viewModelScope.launch(Dispatchers.IO){
            val isSuccess = authRepositoryImpl
                .signInWithEmailPassword(
                    email = signUiState.email,
                    password = signUiState.password
                )
            withContext(Dispatchers.Main){
                if(isSuccess) navController.navigate(route = Hub)
            }
        }
    }

}
