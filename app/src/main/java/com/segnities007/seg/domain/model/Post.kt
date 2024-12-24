package com.segnities007.seg.domain.model

data class Post(
    val id: Int,
    val userId: Int,
    val description: String,
    val hashtags: List<Int>,
)