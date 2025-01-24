package com.segnities007.seg.ui.screens.hub.trend

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
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
    commonPadding: Dp = dimensionResource(R.dimen.padding_normal),
    onHubNavigate: (Route) -> Unit,
) {
    LaunchedEffect(Unit) {
        trendUiAction.onResetReadMore()
        trendUiAction.onGetTrendPostOfToday(3)
        trendUiAction.onGetTrendPostOfWeek(3)
        trendUiAction.onGetTrendPostOfMonth(3)
        trendUiAction.onGetTrendPostOfYear(3)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){

        SubTitle(textID = R.string.todays_most_view_post)
        for(post in trendUiState.trendOfToday)
            PostCard(
                post = post,
                myself = hubUiState.user,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
            )
        if (!trendUiState.isReadMoreAboutTrendOfToday) {
            ReadMoreButton(
                onClick = {
                    trendUiAction.onReadMoreAboutTrendOfToday()
                    trendUiAction.onGetTrendPostOfToday(10)
                },
            )
        }

        Spacer(Modifier.padding(commonPadding))

        SubTitle(textID = R.string.weeks_most_view_post)
        for(post in trendUiState.trendOfWeek)
            PostCard(
                post = post,
                myself = hubUiState.user,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
            )
        if (!trendUiState.isReadMoreAboutTrendOfWeek) {
            ReadMoreButton(
                onClick = {
                    trendUiAction.onReadMoreAboutTrendOfWeek()
                    trendUiAction.onGetTrendPostOfToday(10)
                },
            )
        }

        Spacer(Modifier.padding(commonPadding))

        SubTitle(textID = R.string.months_most_view_post)
        for(post in trendUiState.trendOfMonth)
            PostCard(
                post = post,
                myself = hubUiState.user,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
            )
        if (!trendUiState.isReadMoreAboutTrendOfMonth) {
            ReadMoreButton(
                onClick = {
                    trendUiAction.onReadMoreAboutTrendOfMonth()
                    trendUiAction.onGetTrendPostOfToday(10)
                },
            )
        }

        Spacer(Modifier.padding(commonPadding))

        SubTitle(textID = R.string.years_most_view_post)
        for(post in trendUiState.trendOfYear)
            PostCard(
                post = post,
                myself = hubUiState.user,
                onHubNavigate = onHubNavigate,
                hubUiAction = hubUiAction,
                postCardUiAction = postCardUiAction,
                engagementIconState = engagementIconState,
                engagementIconAction = engagementIconAction,
            )
        if (!trendUiState.isReadMoreAboutTrendOfYear) {
            ReadMoreButton(
                onClick = {
                    trendUiAction.onReadMoreAboutTrendOfYear
                    trendUiAction.onGetTrendPostOfToday(10)
                },
            )
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

private fun mostViewedPost() {
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
