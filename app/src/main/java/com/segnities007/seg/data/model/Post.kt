package com.segnities007.seg.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,                // Post ID
    val description: String?,   // Contents of Post
    val images: List<String>,   // Contents of Post
    val hashTags: List<Int>,    // Hashtag of Post
    val createAt: LocalDate,    // Create date
    val updateAt: LocalDate,    // Update date
)