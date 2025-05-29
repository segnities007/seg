package com.example.feature.components.card.tanka

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

@Composable
fun TankaCard(
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
                Tanka()
                ActionIcons(onProcessOfEngagementAction = onProcessOfEngagementAction)
            }
        }
    }
}

@Composable
fun PostCardScope.Tanka() {
    val firstPhrase = post.description.substring(0..4)
    val secondPhrase = post.description.substring(5..11)
    val thirdPhrase = post.description.substring(12..16)
    val forthPhrase = post.description.substring(17..23)
    val fifthPhrase = post.description.substring(24..30)

    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.weight(1f))
        Phrase(forthPhrase)
        Phrase(fifthPhrase)
        Spacer(Modifier.padding(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Phrase(firstPhrase)
            Phrase(secondPhrase)
            Phrase(thirdPhrase)
        }
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
private fun TankaPreview() {
    TankaCard(
        post = Post(description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
        hubState = HubState(),
        isIncrementView = false,
        onHubAction = {},
        onPostCardAction = {},
        onHubNavigate = {},
        onProcessOfEngagementAction = {},
    )
}
