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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun PostCard(
    post: Post,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    isIncrementView: Boolean = true,
    onHubNavigate: (Route) -> Unit,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
) {
    PostCardUi(
        post = post,
        hubUiState = hubUiState,
        hubUiAction = hubUiAction,
        postCardUiAction = postCardUiAction,
        isIncrementView = isIncrementView,
        onHubNavigate = onHubNavigate,
    ) {
        CardContents {
            Column(modifier = Modifier.padding(horizontal = commonPadding)) {
                Name()
                Description()
                ActionIcons(onProcessOfEngagementAction = onProcessOfEngagementAction)
            }
        }
    }
}

@Composable
fun PostCardUi(
    post: Post,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    postCardUiAction: PostCardUiAction,
    isIncrementView: Boolean = true,
    onHubNavigate: (Route) -> Unit,
    content: @Composable PostCardScope.() -> Unit,
) {
    val scope =
        DefaultPostCardScope(
            post = post,
            commonPadding = dimensionResource(R.dimen.padding_sn),
            hubUiState = hubUiState,
            hubUiAction = hubUiAction,
            postCardUiAction = postCardUiAction,
            onHubNavigate = onHubNavigate,
        )

    LaunchedEffect(Unit) {
        if (isIncrementView) postCardUiAction.onIncrementViewCount(post)
    }

    ElevatedCard(
        modifier =
            Modifier.padding(
                start = dimensionResource(R.dimen.padding_small),
                top = dimensionResource(R.dimen.padding_smaller),
                end = dimensionResource(R.dimen.padding_small),
            ),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
    ) {
        scope.content()
    }
}

@Composable
fun PostCardScope.CardContents(content: @Composable () -> Unit) {
    Row(
        modifier =
            Modifier
                .padding(commonPadding)
                .clickable {
                    hubUiAction.onSetComment(post)
                    postCardUiAction.onClickPostCard(onHubNavigate)
                }.fillMaxWidth(),
    ) {
        AsyncImage(
            modifier =
                Modifier
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
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCardScope.BottomSheet(
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
            modifier =
                Modifier
                    .padding(vertical = commonPadding)
                    .fillMaxWidth()
                    .padding(vertical = commonPadding),
            iconID = R.drawable.baseline_delete_24,
            textID = R.string.delete,
            onClick = {
                postCardUiAction.onDeletePost(post)
                onClickDetailButton()
            },
        )
        Spacer(Modifier.padding(commonPadding))
        SmallButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = commonPadding),
            textID = R.string.cancel,
            onClick = {
                onClickDetailButton()
            },
        )
        Spacer(Modifier.padding(commonPadding))
    }
}

@Composable
fun PanelButton(
    modifier: Modifier = Modifier,
    iconID: Int,
    textID: Int,
    onClick: () -> Unit,
    commonPadding: Dp = dimensionResource(R.dimen.padding_smaller),
) {
    Row(
        modifier =
            modifier
                .padding(vertical = commonPadding)
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
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        Text(
            text = stringResource(textID),
            fontSize = dimensionResource(R.dimen.text_small).value.sp,
        )
    }
}

@Composable
fun PostCardScope.ActionIcons(onProcessOfEngagementAction: (newPost: Post) -> Unit) {
    val counts: List<Int> =
        listOf(
            post.likeCount,
            post.repostCount,
            post.commentCount,
            post.viewCount,
        )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        ActionIcon(
            modifier = Modifier.padding(top = commonPadding),
            painterRes =
                if (hubUiState.user.likes.contains(
                        post.id,
                    )
                ) {
                    EngagementIconState.pushIcons[0]
                } else {
                    EngagementIconState.unPushIcons[0]
                },
            count = counts[0],
            onClick = {
                postCardUiAction.onLike(
                    post,
                    hubUiState,
                    hubUiAction,
                    onProcessOfEngagementAction,
                )
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            modifier = Modifier.padding(top = commonPadding),
            count = counts[1],
            painterRes =
                if (hubUiState.user.reposts.contains(post.id)) {
                    EngagementIconState.pushIcons[1]
                } else {
                    EngagementIconState.unPushIcons[1]
                },
            onClick = {
                postCardUiAction.onRepost(
                    post,
                    hubUiState,
                    hubUiAction,
                    onProcessOfEngagementAction,
                )
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            modifier = Modifier.padding(top = commonPadding),
            count = counts[2],
            painterRes =
                if (hubUiState.user.comments.contains(post.id)) {
                    EngagementIconState.pushIcons[2]
                } else {
                    EngagementIconState.unPushIcons[2]
                },
            onClick = {
                // TODO
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            modifier = Modifier.padding(top = commonPadding),
            painterRes = EngagementIconState.pushIcons[3],
            count = counts[3],
            isClickable = false,
        )
    }
}

@Composable
private fun ActionIcon(
    modifier: Modifier = Modifier,
    painterRes: Int,
    count: Int,
    isClickable: Boolean = true,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                .let { if (isClickable) it.clickable { onClick() } else it }
                .padding(vertical = dimensionResource(R.dimen.padding_action_icon)),
    ) {
        Image(
            painter = painterResource(painterRes),
            contentDescription = null,
            colorFilter = tint(MaterialTheme.colorScheme.secondary),
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_action_icon)))
        Text(count.toString())
    }
}

@Composable
fun PostCardScope.Name() {
    Row(
        modifier = Modifier.padding(vertical = commonPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(post.name)
        Text("@${post.userID}", color = MaterialTheme.colorScheme.secondaryContainer)
    }
}

@Composable
fun PostCardScope.Description() {
    Box(modifier = Modifier.padding(vertical = commonPadding), contentAlignment = Alignment.CenterStart) {
        Text(post.description)
    }
}
