package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Image

interface StorageRepository {
    suspend fun postImages(image: Image, byteArray: ByteArray): String

//    suspend fun getImageUri(imageID: Int): String
    suspend fun deleteImage(imageID: Int)
}