package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val description: String,
    val hashtags: List<Int>,
)