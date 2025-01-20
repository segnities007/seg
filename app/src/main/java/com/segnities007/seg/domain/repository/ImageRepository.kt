package com.segnities007.seg.domain.repository

interface ImageRepository {
    suspend fun postImage(
        byteArray: ByteArray,
        fileName: String,
    ): String

    suspend fun deleteImage(url: String)
}
