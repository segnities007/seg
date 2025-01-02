package com.segnities007.seg.ui.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.segnities007.seg.Hub
import com.segnities007.seg.data.model.User
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import com.segnities007.seg.data.repository.UserRepositoryImpl
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import javax.inject.Inject

// class for SignIn and SignUp UI

data class SignUiState(
    val email: String = "",
    val password: String = "",
)

data class SignUiAction(
    val onPasswordChange: (password: String) -> Unit,
    val onEmailChange: (email: String) -> Unit,
    val onLoginWithGoogle: (context: Context) -> Unit,
    val onSignUpWithEmailPassword: () -> Unit,
    val onSignInWithEmailPassword: (navController: NavHostController) -> Unit,
)

// class for navigation

data class NavigateState(
    val index: Int = 0,
)

data class NavigateAction(
    val onIndexChange: (index: Int) -> Unit,
)

// class for ConfirmEmail UI

data class ConfirmEmailUiAction(
    val confirmEmail: () -> Unit,
)

// class for CreateAccount UI

data class CreateAccountUiState(
    val isShow: Boolean = false,
    val name: String = "",
    val birthday: LocalDate = LocalDate.now(),
)

data class CreateAccountUiAction(
    val onDatePickerOpen: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
    val onNameChange: (name: String) -> Unit,
    val onBirthdayChange: (birthday: LocalDate) -> Unit,
    val createUser: (navController: NavHostController) -> Unit,
)

//

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl,
) : TopLayerViewModel() {

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

    fun getConfirmEmailUiAction(): ConfirmEmailUiAction {
        return ConfirmEmailUiAction(
            confirmEmail = this::confirmEmail
        )
    }

    fun getSignUiAction(): SignUiAction {

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
            onDateSelect = this::onDateSelect,
            onNameChange = this::onNameChange,
            onBirthdayChange = this::onBirthdayChange,
            createUser = this::createUser,
        )

    }

    private fun onDateSelect(millis: Long?){
        val instant = Instant.fromEpochMilliseconds(millis!!)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val year = localDateTime.year
        val month = localDateTime.monthNumber
        val day = localDateTime.dayOfMonth

        onBirthdayChange(LocalDate.of(year, month, day))
    }

    private fun confirmEmail(){

        viewModelScope.launch(Dispatchers.IO){
            authRepositoryImpl.signInWithEmailPassword(
                    email = signUiState.email,
                    password = signUiState.password,
                )
            withContext(Dispatchers.Main){
                val isConfirmed = userRepositoryImpl.confirmEmail()
                if(isConfirmed) onIndexChange(3)
            }
        }

    }

    private fun createUser(
        navController: NavHostController,
    ){

        val user = User(
            id = "",
            name = createAccountUiState.name,
            birthday = createAccountUiState.birthday,
        )

        viewModelScope.launch(Dispatchers.IO){
            val isSuccess = userRepositoryImpl.createUser(user)
            if (isSuccess) {
                withContext(Dispatchers.Main){
                    navController.navigate(route = Hub)
                }
            }
        }

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

    private fun onSignUpWithEmailPassword(){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                val isSuccess = authRepositoryImpl
                    .signUpWithEmailPassword(
                        email = signUiState.email,
                        password = signUiState.password
                    )
                if(isSuccess) onIndexChange(2)
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
