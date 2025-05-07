package com.example.domain.model.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Genre {
    @SerialName("NORMAL")
    NORMAL,
    HAIKU,
    TANKA,
    DODOITSU,
    SEDOUKA,
}
