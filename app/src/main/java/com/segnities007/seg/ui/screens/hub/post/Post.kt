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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.navigation.hub.NavigationHubRoute
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Post(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    postCardUiAction: PostCardUiAction,
    postViewModel: PostViewModel = hiltViewModel(),
    onNavigate: (Route) -> Unit, // go to home
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopToolBar(
            postUiAction = postViewModel.getPostUiAction(),
            hubUiState = hubUiState,
            onNavigate = onNavigate,
            postCardUiAction = postCardUiAction,
        )
        InputField(
            modifier = Modifier.weight(1f), // 余った枠埋め
            postUiState = postViewModel.postUiState,
            postUiAction = postViewModel.getPostUiAction(),
        )
    }
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
) {
    TextField(
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)).fillMaxSize(),
        value = postUiState.inputText,
        onValueChange = { postUiAction.onUpdateInputText(it) },
        label = { Text(stringResource(R.string.post_label)) },
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
private fun TopToolBar(
    modifier: Modifier = Modifier,
    hubUiState: HubUiState,
    postUiAction: PostUiAction,
    postCardUiAction: PostCardUiAction,
    onNavigate: (Route) -> Unit,
) {
    Row(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)).fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallButton(textID = R.string.clear, onClick = { postUiAction.onUpdateInputText("") })
        Spacer(modifier = Modifier.weight(1f))
        SmallButton(
            textID = R.string.post,
            onClick = {
                postUiAction.onCreatePost(hubUiState.user)
                postUiAction.onUpdateInputText("")
                onNavigate(NavigationHubRoute.Home())
            },
        )
    }
}
