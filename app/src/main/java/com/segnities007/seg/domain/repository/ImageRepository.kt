package com.segnities007.seg.domain.repository

import com.segnities007.seg.data.model.Image

interface ImageRepository {
    suspend fun postImage(byteArray: ByteArray): Image

    suspend fun getImage(imageID: Int): Image

    suspend fun getImages(imageIDs: List<Int>): List<Image>

    suspend fun updateImage(image: Image)

    suspend fun deleteImage(imageID: Int)
}
