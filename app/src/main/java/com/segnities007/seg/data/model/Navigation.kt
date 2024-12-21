package com.segnities007.seg.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Navigation {
    @Serializable
    object Splash : Navigation()

    @Serializable
    object Login : Navigation()

    @Serializable
    object Hub : Navigation()
}




