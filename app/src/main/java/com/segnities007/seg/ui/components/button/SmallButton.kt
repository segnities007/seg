package com.segnities007.seg.ui.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.segnities007.seg.R

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    textID: Int,
    fontSize: Int = 16,
    onClick: () -> Unit,
    elevation: Dp = dimensionResource(R.dimen.elevation_small),
    buttonSize: Dp = dimensionResource(R.dimen.button_height_small_size),
) {
    ElevatedButton(
        modifier = modifier.height(buttonSize),
        onClick = onClick,
        elevation = ButtonDefaults.elevatedButtonElevation(elevation),
    ) {
        Text(
            text = stringResource(textID),
            fontSize = fontSize.sp
        )
    }
}