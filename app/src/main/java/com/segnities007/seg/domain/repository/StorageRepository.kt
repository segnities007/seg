package com.segnities007.seg.domain.repository

interface StorageRepository {
    suspend fun postImage(
        fileName: String,
        byteArray: ByteArray,
    ): String

    suspend fun deleteImage(url: String)
}
