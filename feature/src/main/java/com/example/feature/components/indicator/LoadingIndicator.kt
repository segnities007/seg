package com.example.feature.components.indicator

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingUI(
    // for fetching post
    modifier: Modifier = Modifier,
    onLoading: () -> Unit,
) {
    LaunchedEffect(Unit) {
        onLoading()
    }
    CircularProgressIndicator(modifier = modifier.width(32.dp))
}
