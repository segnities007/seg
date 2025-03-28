package com.segnities007.seg.ui.components.card.postcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.data.model.Post
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction
import com.segnities007.seg.ui.screens.hub.setting.my_posts.MyPostsAction

@Composable
fun PostCardWithDetailButton(
    post: Post,
    hubState: HubState,
    onMyPostsAction: (MyPostsAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
) {
    var isShowBottomSheet by remember { mutableStateOf(false) }
    val toggleIsShowBottomSheet = { isShowBottomSheet = !isShowBottomSheet }

    PostCardUi(
        post = post,
        hubState = hubState,
        onHubAction = onHubAction,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
    ) {
        if (isShowBottomSheet) {
            BottomSheet(
                onClickDetailButton = toggleIsShowBottomSheet,
                onHomeAction = onHomeAction,
                onHubAction = onHubAction,
                hubState = hubState,
                onMyPostsAction = onMyPostsAction,
            )
        }

        Box {
            CardContents {
                Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_sn))) {
                    Name()
                    Description()
                    ActionIcons(onProcessOfEngagementAction = onProcessOfEngagementAction)
                }
            }
            DetailButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = toggleIsShowBottomSheet,
            )
        }
    }
}

@Composable
private fun DetailButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(painter = painterResource(R.drawable.baseline_more_vert_24), contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCardScope.BottomSheet(
    hubState: HubState,
    onMyPostsAction: (MyPostsAction) -> Unit,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
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
                onPostCardAction(
                    PostCardAction.DeletePost(
                        post,
                        onMyPostsAction,
                        onHomeAction,
                        hubState,
                        onHubAction,
                    ),
                )
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
