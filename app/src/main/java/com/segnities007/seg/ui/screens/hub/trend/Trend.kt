package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Route
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.EngagementIconAction
import com.segnities007.seg.ui.components.card.EngagementIconState
import com.segnities007.seg.ui.components.card.PostCard
import com.segnities007.seg.ui.components.card.PostCardUiAction
import com.segnities007.seg.ui.screens.hub.HubUiAction
import com.segnities007.seg.ui.screens.hub.HubUiState

@Composable
fun Trend(
    modifier: Modifier,
    hubUiState: HubUiState,
    hubUiAction: HubUiAction,
    trendUiState: TrendUiState,
    trendUiAction: TrendUiAction,
    engagementIconState: EngagementIconState,
    engagementIconAction: EngagementIconAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Route) -> Unit,
) {
    val postLists =
        listOf(
            trendUiState.trendOfToday,
            trendUiState.trendOfWeek,
            trendUiState.trendOfMonth,
            trendUiState.trendOfYear,
        )

    val readMores =
        listOf(
            trendUiState.isReadMoreAboutTrendOfToday,
            trendUiState.isReadMoreAboutTrendOfWeek,
            trendUiState.isReadMoreAboutTrendOfMonth,
            trendUiState.isReadMoreAboutTrendOfYear,
        )

    val onClicks =
        listOf(
            {
                trendUiAction.onReadMoreAboutTrendOfToday()
                trendUiAction.onGetTrendPostOfToday(10)
            },
            {
                trendUiAction.onReadMoreAboutTrendOfWeek()
                trendUiAction.onGetTrendPostOfWeek(10)
            },
            {
                trendUiAction.onReadMoreAboutTrendOfMonth()
                trendUiAction.onGetTrendPostOfMonth(10)
            },
            {
                trendUiAction.onReadMoreAboutTrendOfYear()
                trendUiAction.onGetTrendPostOfYear(10)
            },
        )

    val pagerState =
        rememberPagerState(pageCount = {
            4
        })

    LaunchedEffect(Unit) {
        trendUiAction.onResetReadMore()
        trendUiAction.onGetTrendPostOfToday(3)
        trendUiAction.onGetTrendPostOfWeek(3)
        trendUiAction.onGetTrendPostOfMonth(3)
        trendUiAction.onGetTrendPostOfYear(3)
    }

    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
    ) { page ->
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            SubTitle(textID = trendUiState.textIDs[page])
            for (post in postLists[page]) {
                PostCard(
                    post = post,
                    myself = hubUiState.user,
                    onHubNavigate = onHubNavigate,
                    isIncrementView = readMores[page],
                    hubUiAction = hubUiAction,
                    postCardUiAction = postCardUiAction,
                    engagementIconState = engagementIconState,
                    engagementIconAction = engagementIconAction,
                )
            }
            if (!readMores[page]) {
                ReadMoreButton(
                    onClick = onClicks[page],
                )
            }
        }
    }
}

@Composable
private fun SubTitle(
    modifier: Modifier = Modifier,
    textID: Int,
) {
    Box(modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)).fillMaxWidth()) {
        Text(text = stringResource(textID))
    }
}

@Composable
private fun ReadMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    SmallButton(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_normal)).fillMaxWidth(),
        textID = R.string.read_more,
        onClick = onClick,
    )
}
