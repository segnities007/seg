package com.segnities007.seg.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,             // UUID
    val name: String,           // User Name
    val birthday: LocalDate,    // Birth day
    val userInfoId: Int?,       // ID of User Information
)