package com.example.feature.screens.login.sign_up.create_account

import android.net.Uri
import com.example.feature.model.UiStatus
import java.time.LocalDate

data class CreateAccountState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val isShow: Boolean = false,
    val name: String = "",
    val userID: String = "",
    val birthday: LocalDate = LocalDate.now(),
    val uri: Uri? = null,
    val path: String = "",
    val byteArray: ByteArray? = null,
)
