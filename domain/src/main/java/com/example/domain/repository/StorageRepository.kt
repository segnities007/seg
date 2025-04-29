package com.example.domain.repository

interface StorageRepository {
    suspend fun postAvatarImage(
        path: String,
        byteArray: ByteArray,
    ): String

    suspend fun deleteImage(url: String) {
    }
}
