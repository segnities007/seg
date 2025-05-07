package com.example.domain.model.post

import java.time.LocalDateTime

data class Post(
    val id: Int = 0,
    val name: String = "NoName",
    val userID: String = "NoUser",
    val iconURL: String = "https://avatars.githubusercontent.com/u/174174755?v=4", // default image
    val description: String = "",
    val likeCount: Int = 0,
    val repostCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val createAt: LocalDateTime = LocalDateTime.now(),
    val updateAt: LocalDateTime = LocalDateTime.now(),
    val commentIDs: List<Int> = listOf(),
    val genre: Genre = Genre.NORMAL,
)
