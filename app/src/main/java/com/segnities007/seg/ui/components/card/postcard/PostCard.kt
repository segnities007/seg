package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.imageLoader
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.data.model.User
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun PostCard(
    modifier: Modifier = Modifier.padding(dimensionResource(R.dimen.padding_sn)),
    post: Post,
    myself: User,
    hubUiAction: HubUiAction,
    isShowDetailButton: Boolean = false,
    isIncrementView: Boolean = true, // For disable when view my post
    postCardUiAction: PostCardUiAction,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
    onHubNavigate: (Route) -> Unit,
) {
    var isShowBottomSheet by remember { mutableStateOf(false) }
    val onClickDetailButton = {
        isShowBottomSheet = !isShowBottomSheet
    }

    LaunchedEffect(Unit) {
        if (isIncrementView) postCardUiAction.onIncrementViewCount(post)
    }

    if (isShowBottomSheet) BottomSheet(post = post, onClickDetailButton = onClickDetailButton, postCardUiAction = postCardUiAction)

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
                            hubUiAction.onSetComment(post)
                            postCardUiAction.onClickPostCard(onHubNavigate)
                        }.fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier =
                        modifier
                            .size(dimensionResource(R.dimen.icon_sn))
                            .clip(CircleShape)
                            .clickable {
                                hubUiAction.onSetUserID(post.userID) // for viewing other user
                                hubUiAction.onChangeCurrentRouteName(NavigationHubRoute.Account().name)
                                onHubNavigate(NavigationHubRoute.Account())
                            },
                    model = post.iconURL,
                    contentDescription = post.iconURL,
                    contentScale = ContentScale.Crop,
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Name(modifier = modifier, post = post)
                    Description(modifier = modifier, post = post)
                    Images(modifier = modifier, imageURLs = post.imageURLs)
                    ActionIcons(
                        modifier = modifier,
                        post = post,
                        myself = myself,
                        hubUiAction = hubUiAction,
                        postCardUiAction = postCardUiAction,
                        onProcessOfEngagementAction = onProcessOfEngagementAction
                    )
                }
            }
            if (isShowDetailButton) DetailButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onClickDetailButton)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(
    post: Post,
    postCardUiAction: PostCardUiAction,
    onClickDetailButton: () -> Unit,
    commonPadding: Dp = dimensionResource(R.dimen.padding_sn),
) {
    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        onDismissRequest = {
            onClickDetailButton()
        },
    ) {
        PanelButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = commonPadding),
            iconID = R.drawable.baseline_delete_24,
            textID = R.string.delete,
            onClick = {
                postCardUiAction.onDeletePost(post)
                onClickDetailButton()
            },
        )
        Spacer(Modifier.padding(commonPadding))
        SmallButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = commonPadding),
            textID = R.string.cancel,
            onClick = {
                onClickDetailButton()
            },
        )
        Spacer(Modifier.padding(commonPadding))
    }
}

@Composable
private fun PanelButton(
    modifier: Modifier = Modifier,
    iconID: Int,
    textID: Int,
    onClick: () -> Unit,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
) {
    Row(
        modifier =
            modifier
                .height(dimensionResource(R.dimen.button_height_small_size))
                .clickable {
                    onClick()
                },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Spacer(modifier = Modifier.padding(commonPadding))
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_smaller)),
            painter = painterResource(iconID),
            contentDescription = "Delete post",
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        Text(
            text = stringResource(textID),
            fontSize = dimensionResource(R.dimen.text_small).value.sp,
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
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
) {
    val counts: List<Int> =
        listOf(
            post.likeCount,
            post.repostCount,
            post.commentCount,
            post.viewCount,
        )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        ActionIcon(
            painterRes = if (myself.likes.contains(post.id)) EngagementIconState.pushIcons[0] else EngagementIconState.unPushIcons[0],
            contentRes = EngagementIconState.contentDescriptions[0],
            count = counts[0],
            onClick = {
                postCardUiAction.onLike(
                    post,
                    myself,
                ) {
                    onProcessOfEngagementAction(it)
                    if (myself.likes.contains(post.id)) {
                        hubUiAction.onRemovePostIDFromMyLikes(post.id)
                    } else {
                        hubUiAction.onAddPostIDToMyLikes(post.id)
                    }
                }
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            painterRes = if (myself.reposts.contains(post.id)) EngagementIconState.pushIcons[1] else EngagementIconState.unPushIcons[1],
            contentRes = EngagementIconState.contentDescriptions[1],
            count = counts[1],
            onClick = {
                postCardUiAction.onRepost(
                    post,
                    myself,
                ) {
                    onProcessOfEngagementAction(it)
                    if (myself.reposts.contains(post.id)) {
                        hubUiAction.onRemovePostIDFromMyReposts(post.id)
                    } else {
                        hubUiAction.onAddPostIDToMyReposts(post.id)
                    }
                }
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            painterRes = if (myself.comments.contains(post.id)) EngagementIconState.pushIcons[2] else EngagementIconState.unPushIcons[2],
            contentRes = EngagementIconState.contentDescriptions[2],
            count = counts[2],
            onClick = {
                // TODO
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            painterRes = EngagementIconState.pushIcons[3],
            contentRes = EngagementIconState.contentDescriptions[3],
            count = counts[3],
            isClickable = false,
        )
        Spacer(Modifier.weight(1f))
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
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(post.description)
    }
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
private fun DetailButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_more_vert_24),
            contentDescription = "more vert",
        )
    }
}
