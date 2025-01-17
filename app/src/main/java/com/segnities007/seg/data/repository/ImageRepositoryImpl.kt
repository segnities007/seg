package com.segnities007.seg.data.repository

import android.util.Log
import com.segnities007.seg.data.model.Image
import com.segnities007.seg.domain.repository.ImageRepository
import com.segnities007.seg.domain.repository.StorageRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ImageRepositoryImpl
    @Inject
    constructor(
        private val postgrest: Postgrest,
        private val storageRepository: StorageRepository,
    ) : ImageRepository {
        private val tag = "ImageRepositoryImpl"
        private val tableName = "images"

        override suspend fun postImage(byteArray: ByteArray): Image {
            try {
                val image = Image()

                // get new image added id
                val result =
                    postgrest
                        .from(tableName)
                        .insert(image) {
                            select()
                        }.decodeSingle<Image>()

                // get url
                val path = storageRepository.postImages(result, byteArray)
                val newImage = result.copy(imageUrl = path)

                // set image for url
                updateImage(newImage)

                return newImage
            } catch (e: Exception) {
                Log.d(tag, "failed to create image. error message is $e")
                throw e
            }
        }

        override suspend fun getImage(imageID: Int): Image {
            try {
                val result =
                    postgrest
                        .from(tableName)
                        .select {
                            filter {
                                Image::id eq imageID
                            }
                        }.decodeSingle<Image>()

                return result
            } catch (e: Exception) {
                Log.d(tag, "failed to get image. error message is $e")
                throw e
            }
        }

        override suspend fun getImages(imageIDs: List<Int>): List<Image> {
            val images = mutableListOf<Image>()

            if (imageIDs.isEmpty()) return listOf()

            for (id in imageIDs) {
                images.add(getImage(id))
            }

            return images
        }

        override suspend fun updateImage(image: Image) {
            postgrest.from(tableName).update({
                Image::imageUrl setTo image.imageUrl
            }) {
                filter {
                    Image::id eq image.id
                }
            }
        }

        override suspend fun deleteImage(imageID: Int) {
            try {
                postgrest.from("images").delete {
                    filter {
                        Image::id eq imageID
                    }
                }
            } catch (e: Exception) {
                Log.d(tag, "failed to delete image. error message is $e")
                throw e
            }
        }
    }
