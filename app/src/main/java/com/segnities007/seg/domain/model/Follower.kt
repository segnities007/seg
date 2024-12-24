package com.segnities007.seg.domain.model

data class Follower(
    val id: Int,
    val followId: Int,
    val followeeId: Int,
)
