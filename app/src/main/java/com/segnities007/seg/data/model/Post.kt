package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Post(

    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = "NoName",

    @SerialName("user_id")
    val userID: String = "NoUser",

    @SerialName("icon_url")
    val iconUrl: String = "https://avatars.githubusercontent.com/u/174174755?v=4",

    @SerialName("description")
    val description: String = "NoDescription",

    @SerialName("like_count")
    val likeCount: Int = 0,

    @SerialName("repost_count")
    val repostCount: Int = 0,

    @SerialName("comment_count")
    val commentCount: Int = 0,

    @SerialName("view_count")
    val viewCount: Int = 0,

    @SerialName("create_at")
    @Serializable(with = LocalDateSerializer::class)
    val createAt: LocalDate = LocalDate.now(),

    @SerialName("update_at")
    @Serializable(with = LocalDateSerializer::class)
    val updateAt: LocalDate = LocalDate.now(),

    @SerialName("image_url_list")
    val images: List<String> = listOf(),

    @SerialName("comment_id_list")
    val comments: List<Int> = listOf(),

    @SerialName("hash_tag_id_list")
    val hashTags: List<Int> = listOf(),

)

