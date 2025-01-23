package com.segnities007.seg.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
@Immutable
data class User(
    val id: String = "", // UUID を表す文字列
    @SerialName("user_id")
    val userID: String = "NoID", // ＠は後付
    val name: String = "NoName",
    val description: String = "",
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate? = null,
    @SerialName("is_prime")
    val isPrime: Boolean = false,
    @SerialName("icon_url")
    val iconURL: String = "https://avatars.githubusercontent.com/u/174174755?v=4", // default image
    @SerialName("follow_user_id_list")
    val follows: List<String> = listOf(),
    @SerialName("follow_count")
    val followCount: Int = 0,
    @SerialName("follower_user_id_list")
    val followers: List<String> = listOf(),
    @SerialName("follower_count")
    val followerCount: Int = 0,
    @SerialName("post_id_list")
    val posts: List<Int> = listOf(),
    @SerialName("post_like_id_list")
    val likes: List<Int> = listOf(),
    @SerialName("post_repost_id_list")
    val reposts: List<Int> = listOf(),
    @SerialName("post_comment_id_list")
    val comments: List<Int> = listOf(),
    @SerialName("create_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val createAt: LocalDateTime = LocalDateTime.now(),
    @SerialName("update_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateAt: LocalDateTime = LocalDateTime.now(),
)
