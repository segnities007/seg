package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.runtime.Immutable
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User

@Immutable
data class EngagementIconAction(
    val onLike: (post: Post, myself: User, onAddOrRemoveFromMyList: () -> Unit) -> Unit,
    val onRepost: (post: Post, myself: User, onAddOrRemoveFromMyList: () -> Unit) -> Unit,
    val onComment: (post: Post, comment: Post, myself: User, onAddOrRemoveFromMyList: () -> Unit) -> Unit,
)

@Immutable
data class EngagementIconState(
    val pushIcons: List<Int> =
        listOf(
            R.drawable.baseline_favorite_24,
            R.drawable.baseline_repeat_24,
            R.drawable.baseline_chat_bubble_24,
            R.drawable.baseline_bar_chart_24,
        ),
    val unPushIcons: List<Int> =
        listOf(
            R.drawable.baseline_favorite_border_24,
            R.drawable.baseline_repeat_24,
            R.drawable.baseline_chat_bubble_outline_24,
            R.drawable.baseline_bar_chart_24,
        ),
    val contentDescriptions: List<Int> =
        listOf(
            R.string.favorite,
            R.string.repost,
            R.string.comment,
            R.string.view_count,
        ),
)
