package com.example.domain.repository

interface ImageRepository {
    suspend fun postAvatarImage(
        path: String,
        byteArray: ByteArray,
    ): String

    suspend fun deleteImage(url: String)
}
