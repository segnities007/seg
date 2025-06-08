package com.example.feature.components.button.rounded

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.feature.R
import com.example.feature.model.UiStatus
import com.example.feature.screens.hub.HubAction

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String = "no text",
    uiStatus: UiStatus = UiStatus.Initial,
    onHubAction: (HubAction) -> Unit,
    onClick: () -> Unit,
) {
    LaunchedEffect(uiStatus) {
        if (uiStatus == UiStatus::Error) onHubAction(HubAction.OpenSnackBar((uiStatus as UiStatus.Error).message))
    }

    when (uiStatus) {
        UiStatus.Success,
        UiStatus.Loading,
        -> LoadingButton(modifier = modifier)

        else -> InitialButton(modifier = modifier, text = text, onClick = onClick)
    }
}

@Composable
private fun InitialButton(
    modifier: Modifier = Modifier,
    text: String = "no text",
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier.height(dimensionResource(R.dimen.button_height_normal_size)),
        onClick = onClick,
        elevation = ButtonDefaults.elevatedButtonElevation(1.dp),
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun LoadingButton(modifier: Modifier = Modifier) {
    ElevatedButton(
        modifier = modifier.height(dimensionResource(R.dimen.button_height_normal_size)),
        enabled = false,
        onClick = { /*nothing*/ },
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
        )
    }
}
