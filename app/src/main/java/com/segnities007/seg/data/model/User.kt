package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val icon: String,
    val userInfoId: Int,
)