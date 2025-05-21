package com.example.data.module

import com.example.domain.model.LocalDateTimeSerializer
import com.example.domain.model.post.Genre
import com.example.domain.model.post.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PostDto(
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
    @SerialName("genre")
    val genre: String = Genre.NORMAL.name,
)

fun PostDto.toPost(): Post {
    val genre: Genre =
        when (this.genre) {
            Genre.TANKA.name -> Genre.TANKA
            Genre.HAIKU.name -> Genre.HAIKU
            Genre.SEDOUKA.name -> Genre.SEDOUKA
            else -> Genre.NORMAL
        }
    return Post(
        id = this.id,
        name = this.name,
        userID = this.userID,
        iconURL = this.iconURL,
        description = this.description,
        likeCount = this.likeCount,
        repostCount = this.repostCount,
        commentCount = this.commentCount,
        viewCount = this.viewCount,
        createAt = this.createAt,
        updateAt = this.updateAt,
        commentIDs = this.commentIDs,
        genre = genre,
    )
}

fun PostDto.fromPost(post: Post): PostDto {
    val genre: String =
        when (post.genre) {
            Genre.TANKA -> Genre.TANKA.name
            Genre.HAIKU -> Genre.HAIKU.name
            Genre.SEDOUKA -> Genre.SEDOUKA.name
            Genre.KATAUTA -> Genre.KATAUTA.name
            else -> Genre.NORMAL.name
        }

    return PostDto(
        id = post.id,
        name = post.name,
        userID = post.userID,
        iconURL = post.iconURL,
        description = post.description,
        likeCount = post.likeCount,
        repostCount = post.repostCount,
        commentCount = post.commentCount,
        viewCount = post.viewCount,
        createAt = post.createAt,
        updateAt = post.updateAt,
        commentIDs = post.commentIDs,
        genre = genre,
    )
}
