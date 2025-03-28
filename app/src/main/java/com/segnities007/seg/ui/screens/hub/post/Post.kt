package com.segnities007.seg.ui.screens.hub.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.postcard.PostCardAction
import com.segnities007.seg.ui.components.indicator.CircleIndicator
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState
import com.segnities007.seg.ui.screens.hub.home.HomeAction

@Composable
fun Post(
    modifier: Modifier = Modifier,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit, // go to home
) {
    val postViewModel: PostViewModel = hiltViewModel()

    PostUi(
        modifier = modifier,
        hubState = hubState,
        postState = postViewModel.postState,
        onHubAction = onHubAction,
        onHomeAction = onHomeAction,
        onPostAction = postViewModel::onPostAction,
        onHubNavigate = onHubNavigate,
    ) {
        Column {
            TopToolBar()
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_label)) },
            )
        }
    }
}

@Composable
fun PostUi(
    modifier: Modifier = Modifier,
    hubState: HubState,
    postState: PostState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostAction: (PostAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
    content: @Composable PostScope.() -> Unit,
) {
    val scope =
        DefaultPostScope(
            hubState = hubState,
            postState = postState,
            onHubAction = onHubAction,
            onHomeAction = onHomeAction,
            onPostAction = onPostAction,
            onHubNavigate = onHubNavigate,
        )

    Column(
        modifier =
            modifier
                .padding(
                    vertical = dimensionResource(R.dimen.padding_smaller),
                    horizontal = dimensionResource(R.dimen.padding_small),
                ).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        scope.content()
    }

    CircleIndicator(isLoading = postState.isLoading)
}

@Composable
fun PostScope.InputField(
    modifier: Modifier,
    label: @Composable () -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxSize(),
        value = postState.inputText,
        onValueChange = { onPostAction(PostAction.UpdateInputText(it)) },
        label = label,
        textStyle = TextStyle.Default.copy(fontSize = 24.sp),
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
    )
}

@Composable
fun PostScope.TopToolBar(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .padding(
                    vertical = dimensionResource(R.dimen.padding_normal),
                ).fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallButton(textID = R.string.clear, onClick = { onPostAction(PostAction.UpdateInputText("")) })
        Spacer(modifier = Modifier.weight(1f))
        SmallButton(
            textID = R.string.post,
            onClick = {
                onPostAction(PostAction.CreatePost(
                    user = hubState.user,
                    onUpdateIsLoading = {onPostAction(PostAction.UpdateIsLoading(it))},
                    onUpdateSelf = {onHubAction(HubAction.GetUser)},
                    onNavigate = onHubNavigate
                ))
            },
        )
    }
}

@Composable
@Preview
private fun PostPreview() {
    PostUi(
        modifier = Modifier,
        hubState = HubState(),
        onHubNavigate = {},
        postState = PostState(),
        onHubAction = {},
        onHomeAction = {},
        onPostAction = {},
    ) {
        Column {
            TopToolBar()
            InputField(modifier = Modifier.weight(1f), label = { Text(stringResource(R.string.post_label)) })
        }
    }
}
