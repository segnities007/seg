package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val followerId: Int,           // フォロワー関係のID
    val followerUserId: String,    // 自分（フォロワー）のID
    val followedUserId: String?,   // 相手（フォローされている側）のID（オプション）
)


