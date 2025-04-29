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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.presentation.Navigation
import com.segnities007.seg.R
import com.segnities007.seg.ui.components.button.SmallButton
import com.segnities007.seg.ui.components.card.postcard.PostCard
import com.segnities007.seg.ui.components.card.postcard.PostCardAction
import com.segnities007.seg.ui.components.indicator.PagingIndicator
import com.segnities007.seg.ui.screens.hub.HubAction
import com.segnities007.seg.ui.screens.hub.HubState

@Composable
fun Trend(
    modifier: Modifier,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_sn)
    val trendViewModel: TrendViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        trendViewModel.onTrendAction(TrendAction.Init)
    }

    DisposableEffect(Unit) {
        onDispose {
            trendViewModel.onTrendAction(TrendAction.Dispose)
        }
    }

    TrendUi(
        modifier = modifier,
        commonPadding = commonPadding,
        onHubNavigate = onHubNavigate,
        hubState = hubState,
        onHubAction = onHubAction,
        onTrendAction = trendViewModel::onTrendAction,
        onPostCardAction = onPostCardAction,
        trendListState = trendViewModel.trendListState,
        trendFlagState = trendViewModel.trendFlagState,
    )
}

@Composable
private fun TrendUi(
    modifier: Modifier,
    commonPadding: Dp,
    trendListState: TrendListState,
    trendFlagState: TrendFlagState,
    hubState: HubState,
    onHubAction: (HubAction) -> Unit,
    onTrendAction: (TrendAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (Navigation) -> Unit,
) {
    val textIDs: List<Int> =
        listOf(
            R.string.todays_most_view_post,
            R.string.weeks_most_view_post,
            R.string.months_most_view_post,
            R.string.years_most_view_post,
        )

    val isGets =
        listOf(
            trendFlagState.isGetMoreAboutTrendOfToday,
            trendFlagState.isGetMoreAboutTrendOfWeek,
            trendFlagState.isGetMoreAboutTrendOfMonth,
            trendFlagState.isGetMoreAboutTrendOfYear,
        )

    val postLists =
        listOf(
            trendListState.trendPostsOfToday,
            trendListState.trendPostsOfWeek,
            trendListState.trendPostsOfMonth,
            trendListState.trendPostsOfYear,
        )

    val onClicks =
        listOf(
            {
                onTrendAction(TrendAction.GetAdditionalTrendPostOfToday)
                onTrendAction(TrendAction.GetTrendPostOfToday(10))
            },
            {
                onTrendAction(TrendAction.GetAdditionalTrendPostOfWeek)
                onTrendAction(TrendAction.GetTrendPostOfWeek(10))
            },
            {
                onTrendAction(TrendAction.GetAdditionalTrendPostOfMonth)
                onTrendAction(TrendAction.GetTrendPostOfMonth(10))
            },
            {
                onTrendAction(TrendAction.GetAdditionalTrendPostOfYear)
                onTrendAction(TrendAction.GetTrendPostOfYear(10))
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
            SubTitle(textID = textIDs[page])
            for (post in postLists[page]) {
                PostCard(
                    post = post,
                    hubState = hubState,
                    onHubNavigate = onHubNavigate,
                    isIncrementView = isGets[page],
                    onHubAction = onHubAction,
                    onPostCardAction = onPostCardAction,
                    onProcessOfEngagementAction = { newPost ->
                        onTrendAction(
                            TrendAction.ProcessOfEngagement(
                                newPost,
                            ),
                        )
                    },
                )
                Spacer(Modifier.padding(dimensionResource(R.dimen.padding_smallest)))
            }
            if (!isGets[page]) {
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
    Box(modifier = modifier
        .padding(dimensionResource(R.dimen.padding_normal))
        .fillMaxWidth()) {
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
