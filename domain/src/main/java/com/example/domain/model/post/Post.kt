package com.example.domain.model.post

import com.example.domain.model.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Post(
    val id: Int = 0,
    val name: String = "NoName",
    @SerialName("user_id")
    val userID: String = "NoUser",
    @SerialName("icon_url")
    val iconURL: String = "https://avatars.githubusercontent.com/u/174174755?v=4", // default image
    val description: String = "",
    @SerialName("like_count")
    val likeCount: Int = 0,
    @SerialName("repost_count")
    val repostCount: Int = 0,
    @SerialName("comment_count")
    val commentCount: Int = 0,
    @SerialName("view_count")
    val viewCount: Int = 0,
    @SerialName("create_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val createAt: LocalDateTime = LocalDateTime.now(),
    @SerialName("update_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateAt: LocalDateTime = LocalDateTime.now(),
    @SerialName("comment_id_list")
    val commentIDs: List<Int> = listOf(),
    @Serializable
    val genre: Genre = Genre.NORMAL,
)
