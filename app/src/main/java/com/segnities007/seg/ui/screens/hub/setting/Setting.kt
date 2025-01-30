package com.segnities007.seg.ui.screens.hub.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.screens.hub.HubUiAction

@Composable
fun Setting(
    modifier: Modifier = Modifier,
    hubUiAction: HubUiAction,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(Unit) {
        hubUiAction.onGetUser()
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
