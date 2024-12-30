package com.segnities007.seg.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
@Serializable
data class Post(
    val id: Int,
    @SerialName("user_id") val userId: String,
    val description: String? = null,

    @SerialName("create_at")
    @Serializable(with = LocalDateSerializer::class)
    val createAt: LocalDate = LocalDate.now(),

    @SerialName("update_at")
    @Serializable(with = LocalDateSerializer::class)
    val updateAt: LocalDate = LocalDate.now()
)