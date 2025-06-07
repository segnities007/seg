package com.example.feature.screens.hub.setting.userinfo

import com.example.feature.model.UiStatus

data class UserInfoState(
    val uiStatus: UiStatus = UiStatus.Initial,
    val name: String = "",
    val userID: String = "",
    val description: String = "",
)
