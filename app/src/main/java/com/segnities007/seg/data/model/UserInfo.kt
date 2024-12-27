package com.segnities007.seg.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,                    // ID of User Information
    val isPrime: Boolean = false,   // Status that user is payer
    val icon: String?,              // Icon of user
    val posts: List<Int>,           // List of Post of user
    val followings: List<Int>,      // Users of following
    val followers: List<Int>,       // Users of followers
    val createAt: LocalDate,        // create date
    val updateAt: LocalDate,        // update date
)