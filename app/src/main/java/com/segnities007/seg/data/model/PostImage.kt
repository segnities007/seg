package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostImage(
    @SerialName("post_id") val postId: Int,
    @SerialName("image_url") val imageUrl: String
)