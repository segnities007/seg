package com.example.feature.components.card.katauta

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
import androidx.compose.ui.unit.TextUnit
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
fun KatautaCard(
    post: Post,
    hubState: HubState,
    isIncrementView: Boolean = true,
    onHubAction: (HubAction) -> Unit,
    onPostCardAction: (PostCardAction) -> Unit,
    onHubNavigate: (NavigationHubRoute) -> Unit,
    onProcessOfEngagementAction: (newPost: Post) -> Unit,
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
                Katauta()
                ActionIcons(onProcessOfEngagementAction = onProcessOfEngagementAction,)
            }
        }
    }
}

@Composable
fun PostCardScope.Katauta() {
    val firstPhrase = post.description.substring(0..4)
    val secondPhrase = post.description.substring(5..11)
    val thirdPhrase = post.description.substring(12..18)

    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.weight(1f))
        Phrase(thirdPhrase)
        Phrase(secondPhrase)
        Phrase(firstPhrase)
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun Phrase(
    phrase: String,
    fontSize: TextUnit = 24.sp,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (c in phrase) {
            Text(text = c.toString(), fontSize = fontSize)
        }
    }
}

@Composable
@Preview
private fun KatautaPreview() {
    KatautaCard(
        post = Post(description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
        hubState = HubState(),
        isIncrementView = false,
        onHubAction = {},
        onPostCardAction = {},
        onHubNavigate = {},
        onProcessOfEngagementAction = {},
    )
}
