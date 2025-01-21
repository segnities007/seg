package com.segnities007.seg.ui.components.card

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.imageLoader
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun PostCard(
    modifier: Modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
    post: Post,
    myself: User,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        postCardUiAction.onIncrementViewCount(post)
    }

    ElevatedCard(
        modifier =
            Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_smaller),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
    ) {
        Row(
            modifier =
                Modifier
                    .clickable {
                        postCardUiAction.onUpdatePost(post)
                        postCardUiAction.onClickPostCard(onHubNavigate)
                    }.fillMaxWidth(),
        ) {
            AsyncImage(
                modifier =
                    Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .size(dimensionResource(R.dimen.icon_small))
                        .clip(CircleShape)
                        .clickable {
                            hubUiAction.onGetUserID(post.userID)
                            hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account().name)
                            onHubNavigate(NavigationHubRoute.Account())
                        },
                model = post.iconURL,
                contentDescription = post.iconURL,
                contentScale = ContentScale.Crop,
            )
            Column {
                Name(modifier = modifier, post = post)
                Description(modifier = modifier, post = post)
                Images(modifier = modifier, imageURLs = post.imageURLs)
                ActionIcons(
                    modifier = modifier,
                    post = post,
                    myself = myself,
                    postCardUiAction = postCardUiAction,
                    hubUiAction = hubUiAction,
                )
            }
        }
    }
}

@Composable
private fun Name(
    modifier: Modifier = Modifier,
    post: Post = Post(),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(post.name)
        Text("@${post.userID}", color = MaterialTheme.colorScheme.secondaryContainer)
    }
}

@Composable
private fun Description(
    modifier: Modifier = Modifier,
    post: Post,
) {
    Text(post.description, modifier = modifier)
}

@Composable
private fun Images(
    modifier: Modifier = Modifier,
    imageURLs: List<String>,
    imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
    for (url in imageURLs) {
        AsyncImage(
            modifier = modifier,
            model = url,
            imageLoader = imageLoader,
            contentDescription = "",
        )
    }
}

@Composable
private fun ActionIcons(
    modifier: Modifier = Modifier,
    myself: User,
    post: Post,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
) {
    val pushIcons =
        listOf(
            R.drawable.baseline_favorite_24,
            R.drawable.baseline_repeat_24,
            R.drawable.baseline_chat_bubble_24,
            R.drawable.baseline_bar_chart_24,
        )

    val unPushIcons =
        listOf(
            R.drawable.baseline_favorite_border_24,
            R.drawable.baseline_repeat_24,
            R.drawable.baseline_chat_bubble_outline_24,
            R.drawable.baseline_bar_chart_24,
        )

    val contentDescriptions =
        listOf(
            R.string.favorite,
            R.string.repost,
            R.string.comment,
            R.string.view_count,
        )

    val counts =
        listOf(
            post.likeCount,
            post.repostCount,
            post.commentCount,
            post.viewCount,
        )

    Row(
        modifier = Modifier.padding(commonPadding).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ActionIcon(
            painterRes = if (myself.likes!!.contains(post.id)) pushIcons[0] else unPushIcons[0],
            contentRes = contentDescriptions[0],
            count = counts[0],
            onClick = {
                if (myself.likes.contains(post.id)) {
                    postCardUiAction.onLike(post, myself) { hubUiAction.onRemovePostIDFromLikeList(post.id) }
                } else {
                    postCardUiAction.onLike(post, myself) { hubUiAction.onAddPostIDToLikeList(post.id) }
                }
            },
        )
        ActionIcon(
            painterRes = if (myself.reposts!!.contains(post.id)) pushIcons[1] else unPushIcons[1],
            contentRes = contentDescriptions[1],
            count = counts[1],
            onClick = {
                Log.d("postcard", post.id.toString())
                if (myself.reposts.contains(post.id)) {
                    postCardUiAction.onRepost(post, myself) { hubUiAction.onRemovePostIDFromRepostList(post.id) }
                } else {
                    postCardUiAction.onRepost(post, myself) { hubUiAction.onAddPostIDToRepostList(post.id) }
                }
            },
        )
        ActionIcon(
            painterRes = if (myself.comments!!.contains(post.id)) pushIcons[2] else unPushIcons[2],
            contentRes = contentDescriptions[2],
            count = counts[2],
            onClick = {
                // TODO
            },
        )
        ActionIcon(
            painterRes = pushIcons[3],
            contentRes = contentDescriptions[3],
            count = counts[3],
            onClick = {
                // Nothing
            },
        )
    }
}

@Composable
private fun ActionIcon(
    modifier: Modifier = Modifier,
    painterRes: Int,
    contentRes: Int,
    count: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                .clickable { onClick() }
                .padding(dimensionResource(R.dimen.padding_action_icon)),
    ) {
        Image(
            painter = painterResource(painterRes),
            contentDescription = stringResource(contentRes),
            colorFilter = tint(MaterialTheme.colorScheme.secondary),
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_action_icon)))
        Text(count.toString())
    }
}
