package com.example.feature.screens.login.sign_up.create_account

import android.net.Uri
import java.time.LocalDate

sealed interface CreateAccountAction {
    data object OpenDatePicker : CreateAccountAction

    data object CloseDatePicker : CreateAccountAction

    data class SetDate(
        val millis: Long?,
    ) : CreateAccountAction

    data class ChangeName(
        val name: String,
    ) : CreateAccountAction

    data class ChangeUserID(
        val userID: String,
    ) : CreateAccountAction

    data class ChangeBirthday(
        val birthday: LocalDate,
    ) : CreateAccountAction

    data class CreateUser(
        val onNavigateToHub: () -> Unit,
    ) : CreateAccountAction

    data class SetUri(
        val uri: Uri,
    ) : CreateAccountAction

    data class SetPicture(
        val path: String,
        val byteArray: ByteArray,
    ) : CreateAccountAction
}
