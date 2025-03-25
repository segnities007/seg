package com.segnities007.seg.ui.screens.hub

import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

data class HubAction(
    val onUpdateSelf: (newSelf: User) -> Unit,
    val onChangeIsHideTopBar: () -> Unit,
    val onResetIsHideTopBar: () -> Unit,
    val onGetUser: () -> Unit,
    val onSetComment: (comment: Post) -> Unit,
    val onSetUserID: (userID: String) -> Unit,
    val onSetAccounts: (accounts: List<String>) -> Unit,
    val onAddPostIDToMyLikes: (postID: Int) -> Unit,
    val onRemovePostIDFromMyLikes: (postID: Int) -> Unit,
    val onAddPostIDToMyReposts: (postID: Int) -> Unit,
    val onRemovePostIDFromMyReposts: (postID: Int) -> Unit,
    val onChangeCurrentRouteName: (newRouteName: String) -> Unit,
)
