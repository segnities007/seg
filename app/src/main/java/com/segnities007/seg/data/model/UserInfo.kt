package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    val isPrime: Boolean,
    val posts: List<Int>,
    val followers: List<Int>,
    val createdAt: String,
    val updatedAt: String,
)