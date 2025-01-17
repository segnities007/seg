package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int = 0,
    @SerialName("image_url")
    val imageUrl: String = "",
)
