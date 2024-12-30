package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostHashtag(
    @SerialName("post_id") val postId: Int,
    @SerialName("hashtag_id") val hashtagId: Int
)