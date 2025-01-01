package com.segnities007.seg.data.model

import java.time.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(

    val id: String = "", // UUID を表す文字列

    val name: String = "NoName",

    @SerialName("user_id")
    val userID: String = "NoID", //＠は後付

    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate? = null,

    @SerialName("is_prime")
    val isPrime: Boolean = false,

    @SerialName("icon_url")
    val iconUrl: String? = null,

    @SerialName("follow_count")
    val followCount: Int = 0,

    @SerialName("follower_count")
    val followerCount: Int = 0,

    @SerialName("create_at")
    @Serializable(with = LocalDateSerializer::class)
    val createAt: LocalDate = LocalDate.now(),

    @SerialName("update_at")
    @Serializable(with = LocalDateSerializer::class)
    val updateAt: LocalDate = LocalDate.now()
)
