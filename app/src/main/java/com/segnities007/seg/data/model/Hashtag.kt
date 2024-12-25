package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Hashtag(
    val id: Int,
    val name: String,
)