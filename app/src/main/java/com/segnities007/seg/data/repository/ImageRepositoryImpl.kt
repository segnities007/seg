package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.StorageRepository
import javax.inject.Inject

class ImageRepositoryImpl
    @Inject
    constructor(
        private val storageRepository: StorageRepository,
    ) : ImageRepository {
        private val tag = "ImageRepositoryImpl"

        override suspend fun postAvatarImage(
            path: String,
            byteArray: ByteArray,
        ): String {
            try {
                val url = storageRepository.postAvatarImage(path, byteArray)
                return url
            } catch (e: Exception) {
                Log.d(tag, "failed postImage $e")
                throw e
            }
        }

        override suspend fun deleteImage(url: String) {
            try {
                storageRepository.deleteImage(url)
            } catch (e: Exception) {
                Log.d(tag, "failed deleteImage $e")
                throw e
            }
        }
    }
