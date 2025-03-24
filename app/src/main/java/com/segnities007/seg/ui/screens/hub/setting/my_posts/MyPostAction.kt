package com.segnities007.seg.ui.screens.hub.setting.my_posts

import androidx.compose.runtime.Immutable
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

@Immutable
data class MyPostsAction(
    val onInit: () -> Unit,
    val onUpdateSelectedTabIndex: (index: Int) -> Unit,
    val onSetSelf: (self: User) -> Unit,
    val onGetPosts: () -> Unit,
    val onRemovePostFromPosts: (post: Post) -> Unit,
    val onGetLikedPosts: () -> Unit,
    val onGetRepostedPosts: () -> Unit,
    val onProcessOfEngagementAction: (newPost: Post) -> Unit,
)
