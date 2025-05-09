package com.example.feature.screens.hub.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.post.Genre
import com.example.domain.presentation.navigation.Navigation
import com.example.feature.R
import com.example.feature.components.button.BasicButton
import com.example.feature.components.indicator.CircleIndicator
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction

@Composable
fun Post(
    modifier: Modifier,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
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
    modifier: Modifier,
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
                )
                .fillMaxSize(),
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
                )
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GenreMenu(postState = postState, onPostAction = onPostAction)
        Spacer(modifier = Modifier.weight(1f))
        BasicButton(
            text = stringResource(R.string.post),
            onClick = {
                onPostAction(
                    PostAction.CreatePost(
                        user = hubState.user,
                        onUpdateIsLoading = { onPostAction(PostAction.UpdateIsLoading(it)) },
                        onUpdateSelf = { onHubAction(HubAction.GetUser) },
                        onNavigate = onHubNavigate,
                    ),
                )
            },
        )
    }
}

@Composable
private fun GenreMenu(
    postState: PostState,
    onPostAction: (PostAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        BasicButton(
            text = postState.genre.name,
            onClick = {
                expanded = !expanded
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            for (genre in Genre.entries) {
                DropdownMenuItem(
                    text = { Text(genre.name) },
                    onClick = {
                        onPostAction(PostAction.UpdateGenre(genre))
                    }
                )
            }
        }
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
            InputField(
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.post_label)) },
            )
        }
    }
}
