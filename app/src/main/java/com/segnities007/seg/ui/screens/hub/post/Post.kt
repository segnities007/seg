package com.segnities007.seg.ui.screens.hub.post

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.PostCardUiAction
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
            postUiState = postViewModel.postUiState,
            postUiAction = postViewModel.getPostUiAction(),
            hubUiState = hubUiState,
            onNavigate = onNavigate,
            postCardUiAction = postCardUiAction,
        )
        InputField(modifier = Modifier.weight(1f), postUiState = postViewModel.postUiState, postUiAction = postViewModel.getPostUiAction())
        BottomToolBar(postUiState = postViewModel.postUiState, postUiAction = postViewModel.getPostUiAction())
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
    postUiState: PostUiState,
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
                postUiAction.onCreatePost(hubUiState.user, postUiState.byteArrayList)
                postUiAction.onGetByteArrayList(listOf())
                postUiAction.onUpdateInputText("")
                onNavigate(NavigationHubRoute.Home())
                postCardUiAction.onGetNewPosts()
            },
        )
    }
}

@Composable
private fun BottomToolBar(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
    maxNumberOfItem: Int = 4,
    context: Context = LocalContext.current,
) {
    // 画像選択
    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxNumberOfItem)) { uris ->
            if (uris.isNotEmpty()) {
                val byteArrayList =
                    uris.mapNotNull { uri ->
                        context.contentResolver.openInputStream(uri)?.use { inputStream ->
                            inputStream.readBytes() // InputStreamをByteArrayに変換
                        }
                    }
                postUiAction.onGetByteArrayList(byteArrayList)
            }
        }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
        modifier = modifier.padding(dimensionResource(R.dimen.padding_smaller)).fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(
                iconResource = R.drawable.baseline_image_24,
                iconDescription = R.string.image,
                onClick = {
                    pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                },
            )

            if (postUiState.selectedUris.isNotEmpty()) {
                IconButton(
                    iconResource = R.drawable.baseline_close_24,
                    iconDescription = R.string.image,
                    onClick = { postUiAction.onGetUris(listOf()) },
                )

                postUiState.selectedUris.forEach { uri ->
                    Box(
                        modifier =
                            Modifier
                                .clickable { /*TODO: 必要なアクション*/ }
                                .size(dimensionResource(R.dimen.icon_normal))
                                .padding(dimensionResource(R.dimen.padding_small)),
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Selected Image",
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IconButton(
    modifier: Modifier = Modifier,
    iconResource: Int,
    iconDescription: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .wrapContentSize()
                .clip(CircleShape)
                .clickable { onClick() },
    ) {
        Icon(
            modifier = modifier.size(dimensionResource(R.dimen.icon_small)),
            painter = painterResource(iconResource),
            contentDescription = stringResource(iconDescription),
        )
    }
}
