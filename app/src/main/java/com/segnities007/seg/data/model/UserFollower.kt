package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserFollower(
    @SerialName("user_id") val userId: String,
    @SerialName("follower_user_id") val followerUserId: String,
)