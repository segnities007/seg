package com.example.feature.components.card.postcard

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.R
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState

@Composable
fun DefaultPostCard(
    post: Post,
    hubState: HubState,
    isIncrementView: Boolean = true,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
) {
    PostCardUi(
        post = post,
        hubState = hubState,
        onHubAction = onHubAction,
        isIncrementView = isIncrementView,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
    ) {
        CardContents {
            Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_sn))) {
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
    hubState: HubState,
    isIncrementView: Boolean = true,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
    content: @Composable PostCardScope.() -> Unit,
) {
    val scope =
        DefaultPostCardScope(
            post = post,
            hubState = hubState,
            onHubAction = onHubAction,
            onPostCardAction = onPostCardAction,
            onHubNavigate = onHubNavigate,
        )

    LaunchedEffect(Unit) {
        if (isIncrementView) onPostCardAction(PostCardAction.IncrementViewCount(post))
    }

    ElevatedCard(
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
                .padding(dimensionResource(R.dimen.padding_sn))
                .clickable {
                    onHubAction(HubAction.SetComment(post))
                    onPostCardAction(PostCardAction.ClickPostCard(onHubNavigate))
                }.fillMaxWidth(),
    ) {
        AsyncImage(
            modifier =
                Modifier
                    .size(dimensionResource(R.dimen.icon_sn))
                    .clip(CircleShape)
                    .clickable {
                        onHubAction(HubAction.SetUserID(post.userID))
                        onHubAction(HubAction.ChangeCurrentRouteName(NavigationHubRoute.Account.name))
                        onHubNavigate(NavigationHubRoute.Account)
                    },
            model = post.iconURL,
            placeholder = painterResource(R.mipmap.segnities007),
            contentDescription = post.iconURL,
            contentScale = ContentScale.Crop,
        )
        content()
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
            post.commentIDs.size,
            post.viewCount,
        )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        ActionIcon(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_sn)),
            painterRes =
                if (hubState.user.likes.contains(
                        post.id,
                    )
                ) {
                    EngagementIconState.pushIcons[0]
                } else {
                    EngagementIconState.unPushIcons[0]
                },
            count = counts[0],
            onClick = {
                onPostCardAction(
                    PostCardAction.ClickLikeIcon(
                        post,
                        hubState,
                        onHubAction,
                        onProcessOfEngagementAction,
                    ),
                )
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_sn)),
            count = counts[1],
            painterRes =
                if (hubState.user.reposts.contains(post.id)) {
                    EngagementIconState.pushIcons[1]
                } else {
                    EngagementIconState.unPushIcons[1]
                },
            onClick = {
                onPostCardAction(
                    PostCardAction.ClickRepostIcon(
                        post,
                        hubState,
                        onHubAction,
                        onProcessOfEngagementAction,
                    ),
                )
            },
        )
        Spacer(Modifier.weight(1f))
        ActionIcon(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_sn)),
            count = counts[2],
            painterRes =
                if (hubState.user.comments.contains(post.id)) {
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
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_sn)),
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
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_sn)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(post.name)
        Text("@${post.userID}", color = MaterialTheme.colorScheme.secondaryContainer)
    }
}

@Composable
fun PostCardScope.Description() {
    Box(
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_sn)),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(post.description)
    }
}

@Composable
@Preview
private fun PostCardPreview() {
    DefaultPostCard(
        post = Post(),
        hubState = HubState(),
        onHubAction = {},
        onPostCardAction = {},
        isIncrementView = false,
        onHubNavigate = {},
        onProcessOfEngagementAction = {},
    )
}
