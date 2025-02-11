package com.segnities007.seg.ui.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.segnities007.seg.R

@Composable
fun FloatingButton(
    iconID: Int,
    iconDescription: String = stringResource(R.string.search),
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier.size(dimensionResource(R.dimen.floating_action_button_default)),
        onClick = onClick,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(dimensionResource(R.dimen.elevation_small)),
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.floating_action_button_image_default)),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            painter = painterResource(iconID),
            contentDescription = iconDescription,
        )
    }
}
