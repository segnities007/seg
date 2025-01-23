package com.segnities007.seg.ui.components.card

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ModalBottomSheet
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
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        // modify this increment for trend
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
        Box {
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
                        engagementIconState = engagementIconState,
                        engagementIconAction = engagementIconAction,
                        hubUiAction = hubUiAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionIcons(
    modifier: Modifier = Modifier,
    myself: User,
    post: Post,
    hubUiAction: HubUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
) {
    val counts: List<Int> =
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
            painterRes = if (myself.likes.contains(post.id)) engagementIconState.pushIcons[0] else engagementIconState.unPushIcons[0],
            contentRes = engagementIconState.contentDescriptions[0],
            count = counts[0],
            onClick = {
                if (myself.likes.contains(post.id)) {
                    engagementIconAction.onLike(post, myself) { hubUiAction.onRemovePostIDFromLikeList(post.id) }
                } else {
                    engagementIconAction.onLike(post, myself) { hubUiAction.onAddPostIDToLikeList(post.id) }
                }
            },
        )
        ActionIcon(
            painterRes = if (myself.reposts.contains(post.id)) engagementIconState.pushIcons[1] else engagementIconState.unPushIcons[1],
            contentRes = engagementIconState.contentDescriptions[1],
            count = counts[1],
            onClick = {
                Log.d("postcard", post.id.toString())
                if (myself.reposts.contains(post.id)) {
                    engagementIconAction.onRepost(post, myself) { hubUiAction.onRemovePostIDFromRepostList(post.id) }
                } else {
                    engagementIconAction.onRepost(post, myself) { hubUiAction.onAddPostIDToRepostList(post.id) }
                }
            },
        )
        ActionIcon(
            painterRes = if (myself.comments.contains(post.id)) engagementIconState.pushIcons[2] else engagementIconState.unPushIcons[2],
            contentRes = engagementIconState.contentDescriptions[2],
            count = counts[2],
            onClick = {
                // TODO
            },
        )
        ActionIcon(
            painterRes = engagementIconState.pushIcons[3],
            contentRes = engagementIconState.contentDescriptions[3],
            count = counts[3],
            isClickable = false,
        )
    }
}

@Composable
private fun ActionIcon(
    modifier: Modifier = Modifier,
    painterRes: Int,
    contentRes: Int,
    count: Int,
    isClickable: Boolean = true,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                .let { if (isClickable) it.clickable { onClick() } else it }
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
