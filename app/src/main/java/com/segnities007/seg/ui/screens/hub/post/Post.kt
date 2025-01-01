package com.segnities007.seg.ui.screens.hub.post

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.segnities007.seg.R
import com.segnities007.seg.ui.screens.hub.PostUiAction
import com.segnities007.seg.ui.screens.hub.PostUiState

@Composable
fun Post(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TopToolBar(postUiAction = postUiAction, postUiState = postUiState)
        InputField(modifier = Modifier.weight(1f), postUiState = postUiState , postUiAction = postUiAction)
        BottomToolBar(modifier = Modifier.imePadding(), postUiState = postUiState, postUiAction = postUiAction)
    }
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
){

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
            .fillMaxSize(),
        value = postUiState.inputText,
        onValueChange = { postUiAction.onInputTextChange(it) },
        label = { Text(stringResource(R.string.post_label)) },
        textStyle = TextStyle.Default.copy(fontSize = 24.sp),
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus() // フォーカスを外す
                keyboardController?.hide() // キーボードを閉じる
            }
        )
    )
}

@Composable
private fun TopToolBar(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
){
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth(),
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElevatedButton(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                onClick = { postUiAction.onInputTextChange("") }
            ) { Text(stringResource(R.string.clear)) }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ){
                ElevatedButton(
                    onClick = {},
                ) { Text(stringResource(R.string.post)) }
            }
        }
    }
}

@Composable
private fun BottomToolBar(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    postUiAction: PostUiAction,
) {
    val maxNumberOfItem = 4
    val selectedUris = remember { mutableStateOf<List<String>>(emptyList()) }
    val pickMultipleMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxNumberOfItem)) { uris ->
        if (uris.isNotEmpty()) {
            selectedUris.value = uris.map { it.toString() }
        }
    }


    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_shape)),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth(),
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

            if (selectedUris.value.isNotEmpty()) {
                IconButton(
                    iconResource = R.drawable.baseline_close_24,
                    iconDescription = R.string.image,
                    onClick = {
                        selectedUris.value = listOf()
                    },
                )

                selectedUris.value.forEach { uri ->

                    Box(
                        modifier = Modifier
                            .clickable { /*TODO*/ }
                            .size(dimensionResource(R.dimen.icon_normal))
                            .padding(dimensionResource(R.dimen.padding_small)),
                    ){
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Selected Image",//TODO Modify
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
){
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(CircleShape)
            .clickable { onClick() }
    ){
        Icon(
            modifier = modifier.size(dimensionResource(R.dimen.icon_small)),
            painter = painterResource(iconResource),
            contentDescription = stringResource(iconDescription),
        )
    }
}

