package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserFollow(
    @SerialName("user_id") val userId: String,
    @SerialName("follows_user_id") val followsUserId: String,
)