package com.segnities007.seg.ui.screens.login.sign_up.create_account

import android.net.Uri
import java.time.LocalDate

data class CreateAccountState(
    val isShow: Boolean = false,
    val name: String = "",
    val userID: String = "",
    val birthday: LocalDate = LocalDate.now(),
    val uri: Uri? = null,
    val path: String = "",
    val byteArray: ByteArray? = null,
)
