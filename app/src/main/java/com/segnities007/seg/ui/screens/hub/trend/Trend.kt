package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState
import com.segnities007.seg.R

@Composable
fun Trend(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    trendUiState: TrendUiState,
    trendUiAction: TrendUiAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
    ) {

    LaunchedEffect(Unit) {
        trendUiAction.onGetTrendPostInThisWeek(3)
        if(trendUiState.isReadMoreAboutTrend) trendUiAction.onReadMoreAboutTrend()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        //trend of today
        item(){
            SubTitle()
        }
        items(
            trendUiState.trends.size,
            key = { index: Int -> trendUiState.trends[index].id },
        ) { i ->
            PostCard(
                post = trendUiState.trends[i],
                myself = hubUiState.user,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
            )
        }
        item(){
            if(!trendUiState.isReadMoreAboutTrend){
                ReadMoreButton(
                    onClick = {
                        trendUiAction.onReadMoreAboutTrend()
                        trendUiAction.onGetTrendPostInThisWeek(10)
                    }
                )
            }
        }
        //

    }
}

@Composable
private fun SubTitle(
    modifier: Modifier = Modifier,
    textID: Int = 0,
){
    Box(modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)).fillMaxWidth(),){
        Text(text = "今日の最も見られたPost10選")
    }
}

@Composable
private fun ReadMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    SmallButton(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)).fillMaxWidth(),
        textID = R.string.read_more,
        onClick = onClick,
    )
}
