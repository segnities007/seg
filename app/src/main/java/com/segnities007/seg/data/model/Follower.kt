package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val id: Int,
    @SerialName("follower_user_id") val followerUserId: String,
    @SerialName("followed_user_id") val followedUserId: String,
)