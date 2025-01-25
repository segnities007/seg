package com.segnities007.seg.ui.components.top_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.segnities007.seg.R

@Composable
@Preview
fun TopSearchBar(
    commonPadding: Dp = dimensionResource(R.dimen.padding_sn),
    onHubBackNavigate: () -> Unit = {},
) {
    TopSearchPanel(
        modifier = Modifier,
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(R.dimen.padding_large)),
            horizontalArrangement = Arrangement.Start,
        ) {
            Spacer(modifier = Modifier.padding(commonPadding))
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = {
                    onHubBackNavigate()
                },
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "back",
                )
            }
            Spacer(modifier = Modifier.padding(commonPadding))
            SearchTextField()
        }
    }
}

@Composable
private fun SearchTextField(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "",
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.padding_smallest)))
                Text(text = "Search")
            }
        },
        onValueChange = {},
        shape = CircleShape,
    )
}

@Composable
private fun TopSearchPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .shadow(elevation = dimensionResource(R.dimen.elevation_nl))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(WindowInsets.statusBars.asPaddingValues()) // ステータスバーを避ける
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
