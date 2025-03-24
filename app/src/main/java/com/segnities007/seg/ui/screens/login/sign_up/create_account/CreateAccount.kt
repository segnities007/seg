package com.segnities007.seg.ui.screens.login.sign_up.create_account

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.indicator.PagingIndicator

@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    onNavigateToHub: () -> Unit,
    createAccountViewModel: CreateAccountViewModel = hiltViewModel(),
) {
    CreateAccountUi(
        modifier = modifier,
        onNavigateToHub = onNavigateToHub,
        createAccountState = createAccountViewModel.createAccountUiState,
        createAccountAction = createAccountViewModel.onGetCreateAccountUiAction(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateAccountUi(
    modifier: Modifier = Modifier,
    onNavigateToHub: () -> Unit,
    createAccountState: CreateAccountState,
    createAccountAction: CreateAccountAction,
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
) {
    val pagerState =
        rememberPagerState(pageCount = {
            3
        })
    DatePickerDialog(
        createAccountState.isShow,
        onDateSelected = createAccountAction.onDateSelect,
        onDatePickerDismiss = createAccountAction.onDatePickerClose,
    )

    HorizontalPager(
        state = pagerState,
    ) { page ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(Modifier.weight(1f))
            when (page) {
                0 ->
                    FirstPage(
                        createAccountState = createAccountState,
                        createAccountAction = createAccountAction,
                        commonPadding = commonPadding,
                    )
                1 ->
                    SecondPage(
                        createAccountAction = createAccountAction,
                        commonPadding = commonPadding,
                    )
                2 ->
                    ThirdPage(
                        createAccountAction = createAccountAction,
                        onNavigateToHub = onNavigateToHub,
                    )
            }
            Spacer(Modifier.weight(1f))
            PagingIndicator(
                currentPageCount = page,
                pageCount = pagerState.pageCount,
            )
            Spacer(Modifier.padding(commonPadding))
        }
    }
}

@Composable
private fun FirstPage(
    createAccountState: CreateAccountState,
    createAccountAction: CreateAccountAction,
    commonPadding: Dp,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputForm(
            text = createAccountState.name,
            label = stringResource(id = R.string.name),
            onValueChange = createAccountAction.onNameChange,
        )
        Spacer(modifier = Modifier.padding(commonPadding))

        InputForm(
            text = createAccountState.userID,
            label = stringResource(id = R.string.user_id),
            onValueChange = createAccountAction.onChangeUserID,
        )
    }
}

@Composable
private fun SecondPage(
    createAccountAction: CreateAccountAction,
    commonPadding: Dp,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImagePickerButton(createAccountAction = createAccountAction)
        Spacer(modifier = Modifier.padding(commonPadding))
        SmallButton(textID = R.string.select_birthday, onClick = createAccountAction.onDatePickerOpen)
    }
}

@Composable
private fun ThirdPage(
    createAccountAction: CreateAccountAction,
    onNavigateToHub: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SmallButton(textID = R.string.enter, onClick = { createAccountAction.onCreateUser(onNavigateToHub) })
    }
}

@Composable
private fun ImagePickerButton(createAccountAction: CreateAccountAction) {
    val context = LocalContext.current
    val tag = "PhotoPicker"

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                try {
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        val byteArray: ByteArray = inputStream.readBytes()
                        createAccountAction.onSetPicture(uri.path.toString(), byteArray)
                        createAccountAction.onSetUri(uri)
                    }
                } catch (e: Exception) {
                    Log.e(tag, " $e")
                }
            }
        }

    SmallButton(
        textID = R.string.select_image,
        onClick = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    isShow: Boolean,
    datePickerState: DatePickerState = rememberDatePickerState(),
    onDateSelected: (Long?) -> Unit,
    onDatePickerDismiss: () -> Unit,
) {
    if (isShow) {
        DatePickerDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = onDatePickerDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDatePickerDismiss()
                    },
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDatePickerDismiss) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun InputForm(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text(label) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // フォーカスを外す
                        keyboardController?.hide()
                    },
                ),
        )
    }
}

@Composable
@Preview
private fun CreateAccountPreview() {
    CreateAccountUi(
        modifier = Modifier,
        onNavigateToHub = {},
        createAccountState = CreateAccountState(),
        createAccountAction =
            CreateAccountAction(
                onDatePickerOpen = {},
                onDatePickerClose = {},
                onDateSelect = {},
                onNameChange = {},
                onChangeUserID = {},
                onBirthdayChange = {},
                onCreateUser = {},
                onSetPicture = { _, _ -> },
                onSetUri = {},
            ),
    )
}
