package com.example.domain.model.post

import kotlinx.serialization.Serializable

@Serializable
enum class Genre {
    NORMAL,
    HAIKU,
    TANKA,
    SEDOUKA,
    KATAUTA,
}
