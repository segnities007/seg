package com.example.feature.components.card.haiku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.post.Post
import com.example.domain.presentation.navigation.NavigationHubRoute
import com.example.feature.R
import com.example.feature.components.card.postcard.ActionIcons
import com.example.feature.components.card.postcard.CardContents
import com.example.feature.components.card.postcard.Name
import com.example.feature.components.card.postcard.PostCardAction
import com.example.feature.components.card.postcard.PostCardScope
import com.example.feature.components.card.postcard.PostCardUi
import com.example.feature.screens.hub.HubAction
import com.example.feature.screens.hub.HubState
import com.example.feature.screens.hub.home.HomeAction

@Composable
fun HaikuCard(
    post: Post,
    hubState: HubState,
    isIncrementView: Boolean = true,
    onHubAction: (HubAction) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
) {
    PostCardUi(
        post = post,
        hubState = hubState,
        isIncrementView = isIncrementView,
        onHubAction = onHubAction,
        onPostCardAction = onPostCardAction,
        onHubNavigate = onHubNavigate,
    ) {
        CardContents {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(horizontal = dimensionResource(R.dimen.padding_sn)),
            ) {
                Name()
                Haiku()
                ActionIcons(
                    onProcessOfEngagementAction = { haiku ->
                        onHomeAction(
                            HomeAction.ChangeEngagementOfHaiku(
                                haiku,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun PostCardScope.Haiku() {
    val upperPhrase = post.description.substring(0..4)
    val middlePhrase = post.description.substring(5..11)
    val lowerPhase = post.description.substring(12..16)
    val fontSize = 32.sp

    val upper: @Composable () -> Unit = {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (c in upperPhrase) {
                Text(text = c.toString(), fontSize = fontSize)
            }
            Spacer(Modifier.weight(1f))
        }
    }

    val middle: @Composable () -> Unit = {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.weight(1f))
            for (c in middlePhrase) {
                Text(text = c.toString(), fontSize = fontSize)
            }
            Spacer(Modifier.weight(1f))
        }
    }

    val lower: @Composable () -> Unit = {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.weight(1f))
            for (c in lowerPhase) {
                Text(text = c.toString(), fontSize = fontSize)
            }
        }
    }

    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.weight(1f))
        lower()
        middle()
        upper()
        Spacer(Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun HaikuPreview() {
    HaikuCard(
        post = Post(description = "abcdefghrjklmnopq"),
        hubState = HubState(),
        isIncrementView = false,
        onHubAction = {},
        onPostCardAction = {},
        onHubNavigate = {},
        onHomeAction = {},
    )
}
