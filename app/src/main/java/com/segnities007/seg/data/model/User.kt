package com.segnities007.seg.data.model

import java.time.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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

    @SerialName("follow_id_list")
    val follows: List<Int> = listOf(),

    @SerialName("follow_count")
    val followCount: Int = 0,

    @SerialName("follower_count")
    val followerCount: Int = 0,

    @SerialName("create_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val createAt: LocalDateTime = LocalDateTime.now(),

    @SerialName("update_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateAt: LocalDateTime = LocalDateTime.now(),

    @SerialName("post_id_list")
    val posts: List<Int> = listOf(),
)
