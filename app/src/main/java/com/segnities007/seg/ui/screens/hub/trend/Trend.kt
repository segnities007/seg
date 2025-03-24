package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.segnities007.seg.R
import com.segnities007.seg.domain.presentation.Navigation
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardUiAction
import com.segnities007.seg.ui.components.indicator.PagingIndicator
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Composable
fun Trend(
    modifier: Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_sn),
    hubState: HubState,
    hubAction: HubAction,
    trendViewModel: TrendViewModel = hiltViewModel(),
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        trendViewModel.onGetTrendUiAction().onResetReadMore()
        trendViewModel.onGetTrendUiAction().onGetTrendPostOfToday(3)
        trendViewModel.onGetTrendUiAction().onGetTrendPostOfWeek(3)
        trendViewModel.onGetTrendUiAction().onGetTrendPostOfMonth(3)
        trendViewModel.onGetTrendUiAction().onGetTrendPostOfYear(3)
    }

    TrendUi(
        modifier = modifier,
        commonPadding = commonPadding,
        hubState = hubState,
        hubAction = hubAction,
        trendState = trendViewModel.trendState,
        trendAction = trendViewModel.onGetTrendUiAction(),
        postCardUiAction = postCardUiAction,
        onHubNavigate = onHubNavigate,
    )
}

@Composable
private fun TrendUi(
    modifier: Modifier,
    commonPadding: Dp,
    hubState: HubState,
    hubAction: HubAction,
    trendState: TrendState,
    trendAction: TrendAction,
    postCardUiAction: PostCardUiAction,
    onHubNavigate: (Navigation) -> Unit,
) {
    val postLists =
        listOf(
            trendState.trendOfToday,
            trendState.trendOfWeek,
            trendState.trendOfMonth,
            trendState.trendOfYear,
        )

    val readMores =
        listOf(
            trendState.isReadMoreAboutTrendOfToday,
            trendState.isReadMoreAboutTrendOfWeek,
            trendState.isReadMoreAboutTrendOfMonth,
            trendState.isReadMoreAboutTrendOfYear,
        )

    val onClicks =
        listOf(
            {
                trendAction.onReadMoreAboutTrendOfToday()
                trendAction.onGetTrendPostOfToday(10)
            },
            {
                trendAction.onReadMoreAboutTrendOfWeek()
                trendAction.onGetTrendPostOfWeek(10)
            },
            {
                trendAction.onReadMoreAboutTrendOfMonth()
                trendAction.onGetTrendPostOfMonth(10)
            },
            {
                trendAction.onReadMoreAboutTrendOfYear()
                trendAction.onGetTrendPostOfYear(10)
            },
        )

    val pagerState =
        rememberPagerState(pageCount = {
            4
        })

    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
    ) { page ->
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            SubTitle(textID = trendState.textIDs[page])
            for (post in postLists[page]) {
                PostCard(
                    post = post,
                    hubState = hubState,
                    onHubNavigate = onHubNavigate,
                    isIncrementView = readMores[page],
                    hubAction = hubAction,
                    postCardUiAction = postCardUiAction,
                    onProcessOfEngagementAction = trendAction.onProcessOfEngagementAction,
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
            if (!readMores[page]) {
                Spacer(Modifier.padding(commonPadding))
                ReadMoreButton(
                    modifier = Modifier.padding(horizontal = commonPadding),
                    onClick = onClicks[page],
                )
            }
            Spacer(Modifier.padding(commonPadding))
            PagingIndicator(
                currentPageCount = page,
                pageCount = pagerState.pageCount,
            )
            Spacer(Modifier.padding(commonPadding))
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
        modifier = modifier.fillMaxWidth(),
        textID = R.string.read_more,
        onClick = onClick,
    )
}
