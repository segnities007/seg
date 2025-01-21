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

        override suspend fun postImage(
            byteArray: ByteArray,
            fileName: String,
        ): String {
            try {
                val url = storageRepository.postImage(fileName, byteArray)
                return url
            } catch (e: Exception) {
                Log.d(tag, "failed to create image. error message is $e")
                throw e
            }
        }

        override suspend fun deleteImage(url: String) {
            // TODO
//            try {
//                postgrest.from("images").delete {
//                    filter {
//                        Image::id eq imageID
//                    }
//                }
//            } catch (e: Exception) {
//                Log.d(tag, "failed to delete image. error message is $e")
//                throw e
//            }
        }
    }
