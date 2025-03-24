package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.segnities007.seg.ui.screens.hub.HubAction

@Composable
fun Setting(
    modifier: Modifier = Modifier,
    hubAction: HubAction,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(Unit) {
        hubAction.onGetUser()
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
