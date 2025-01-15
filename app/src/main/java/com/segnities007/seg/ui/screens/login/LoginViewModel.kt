package com.segnities007.seg.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import com.segnities007.seg.domain.repository.AuthRepository
import com.segnities007.seg.domain.repository.UserRepository
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

data class LoginUiState(
    val email: String = "",
    val password: String = "",
)

data class LoginUiAction(
    val onPasswordChange: (password: String) -> Unit,
    val onEmailChange: (email: String) -> Unit,
    val onSignUpWithEmailPassword: (onNavigate: () -> Unit,) -> Unit,
    val onSignInWithEmailPassword: (onNavigate: () -> Unit,) -> Unit,
)

// class for ConfirmEmail UI

data class ConfirmEmailUiAction(
    val confirmEmail: (onNavigate: () -> Unit,) -> Unit,
)

// class for CreateAccount UI

data class CreateAccountUiState(
    val isShow: Boolean = false,
    val name: String = "",
    val userID: String = "",
    val birthday: LocalDate = LocalDate.now(),
)

data class CreateAccountUiAction(
    val onDatePickerOpen: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
    val onNameChange: (name: String) -> Unit,
    val onChangeUserID: (userID: String) -> Unit,
    val onBirthdayChange: (birthday: LocalDate) -> Unit,
    val createUser: (onNavigate: () -> Unit,) -> Unit,
)

//

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : TopLayerViewModel() {

    var loginUiState by mutableStateOf(LoginUiState())
        private set
    var createAccountUiState by mutableStateOf(CreateAccountUiState())
        private set

    fun getConfirmEmailUiAction(): ConfirmEmailUiAction {
        return ConfirmEmailUiAction(
            confirmEmail = this::confirmEmail
        )
    }

    fun getLoginAction(): LoginUiAction {

        return LoginUiAction(
            onEmailChange = this::onEmailChange,
            onPasswordChange = this::onPasswordChange,
            onSignUpWithEmailPassword = this::onSignUpWithEmailPassword,
            onSignInWithEmailPassword = this::onSignInWithEmailPassword,
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
            onChangeUserID = this::onChangeUserID,
        )

    }

    private fun onChangeUserID(userID: String){
        createAccountUiState = createAccountUiState.copy(userID = userID)
    }

    private fun onDateSelect(millis: Long?){
        val instant = Instant.fromEpochMilliseconds(millis!!)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val year = localDateTime.year
        val month = localDateTime.monthNumber
        val day = localDateTime.dayOfMonth

        onBirthdayChange(LocalDate.of(year, month, day))
    }

    private fun confirmEmail(onNavigate: () -> Unit, ){
        viewModelScope.launch(Dispatchers.IO){
            authRepository.signInWithEmailPassword(
                    email = loginUiState.email,
                    password = loginUiState.password,
                )
            withContext(Dispatchers.Main){
                val isConfirmed = userRepository.confirmEmail()
                if(isConfirmed) onNavigate()
            }
        }

    }

    private fun createUser(onNavigate: () -> Unit, ){
        val user = User(
            id = "",
            userID = createAccountUiState.userID,
            name = createAccountUiState.name,
            birthday = createAccountUiState.birthday,
        )
        viewModelScope.launch(Dispatchers.IO){
            userRepository.createUser(user)
                withContext(Dispatchers.Main){
                    onNavigate()
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
        loginUiState = loginUiState.copy(email = newEmail)
    }

    private fun onPasswordChange(newPassword: String) {
        loginUiState = loginUiState.copy(password = newPassword)
    }

    private fun onSignUpWithEmailPassword(onNavigate: () -> Unit,){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                val isSuccess = authRepository.signUpWithEmailPassword(
                        email = loginUiState.email,
                        password = loginUiState.password
                    )
                if(isSuccess) onNavigate()
            }
        }
    }

    private fun onSignInWithEmailPassword(onNavigate: () -> Unit, ){
        viewModelScope.launch(Dispatchers.IO){
            val isSuccess = authRepository.signInWithEmailPassword(
                    email = loginUiState.email,
                    password = loginUiState.password
                )
            withContext(Dispatchers.Main){
                if(isSuccess) onNavigate()
            }
        }
    }

}
