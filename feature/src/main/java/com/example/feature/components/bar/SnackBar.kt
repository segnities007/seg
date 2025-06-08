package com.example.feature.components.bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.R

@Composable
fun SnackBar(
    snackBarHostState: SnackbarHostState,
    message: String,
    onDispose: () -> Unit,
) {
    SnackbarHost(
        hostState = snackBarHostState,
        snackbar = { SnackBarContent(message, onDispose) },
    )
}

@Composable
private fun SnackBarContent(
    message: String,
    onDispose: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            modifier =
                Modifier
                    .widthIn(max = 400.dp)
                    .padding(16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        IconButton(onClick = onDispose) {
            Icon(
                modifier = Modifier.padding(0.dp),
                painter = painterResource(R.drawable.baseline_close_24),
                contentDescription = "close",
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SnackBarContentPreview() {
    SnackBarContent(message = "ネットワークを用いた情報取得に失敗しました。") {}
}
