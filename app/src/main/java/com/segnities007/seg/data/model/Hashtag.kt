package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hashtag(
    val id: Int,
    val label: String,
    @SerialName("icon_url") val iconUrl: String? = null
)