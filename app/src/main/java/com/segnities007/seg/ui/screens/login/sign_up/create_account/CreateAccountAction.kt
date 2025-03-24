package com.segnities007.seg.ui.screens.login.sign_up.create_account

import android.net.Uri
import java.time.LocalDate

data class CreateAccountAction(
    val onDatePickerOpen: () -> Unit,
    val onDatePickerClose: () -> Unit,
    val onDateSelect: (Long?) -> Unit,
    val onNameChange: (name: String) -> Unit,
    val onChangeUserID: (userID: String) -> Unit,
    val onBirthdayChange: (birthday: LocalDate) -> Unit,
    val onCreateUser: (onNavigateToHub: () -> Unit) -> Unit,
    val onSetUri: (uri: Uri) -> Unit,
    val onSetPicture: (path: String, byteArray: ByteArray) -> Unit,
)