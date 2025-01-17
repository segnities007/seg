package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Post(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "NoName",
    @SerialName("user_id")
    val userID: String = "NoUser",
    @SerialName("icon_id")
    val iconID: Int = 0,
    @SerialName("description")
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
    @SerialName("image_id_list")
    val imageIDs: List<Int>? = listOf(),
    @SerialName("comment_id_list")
    val comments: List<Int>? = listOf(),
    @SerialName("hash_tag_id_list")
    val hashTags: List<Int>? = listOf(),
)
