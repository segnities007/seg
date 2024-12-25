package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val id: Int,
    val followId: Int,
    val followeeId: Int,
)
