package com.example.feature.screens.hub.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.feature.screens.hub.HubAction

@Composable
fun Setting(
    modifier: Modifier = Modifier,
    onHubAction: (HubAction) -> Unit,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(Unit) {
        onHubAction(HubAction.GetUser(onHubAction))
    }

    Box(
        Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }
}
