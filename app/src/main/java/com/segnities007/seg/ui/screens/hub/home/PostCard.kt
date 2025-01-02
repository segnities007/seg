package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.segnities007.seg.R

@Composable
fun PostCard(
    modifier: Modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    url: String = "https://avatars.githubusercontent.com/u/174174755?v=4",
){
    ElevatedCard(
        modifier = Modifier
            .padding(
                start = dimensionResource(R.dimen.padding_moderate),
                top = dimensionResource(R.dimen.padding_moderate),
                end = dimensionResource(R.dimen.padding_moderate),
            ),
        elevation =  CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
    ){
        Row(modifier = Modifier.clickable { onClick() }.fillMaxWidth(),){
            AsyncImage(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .size(dimensionResource(R.dimen.icon_small))
                    .clip(CircleShape)
                    .clickable { onIconClick() },
                model = url,
                contentDescription = url,
                contentScale = ContentScale.Crop,
            )
            Column(){
                Name(modifier = modifier)
                Description(modifier = modifier)
                Images(modifier = modifier)
                ActionIcons(modifier = modifier)
            }
        }
    }
}

@Composable
private fun Name(
    modifier: Modifier = Modifier,
    name: String = stringResource(R.string.no_name),
    userId: String = stringResource(R.string.no_user_id)
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ){
        Text(name)
        Text(userId, color = MaterialTheme.colorScheme.secondaryContainer)
    }
}

@Composable
private fun Description(
    modifier: Modifier = Modifier,
    description: String = stringResource(R.string.no_description),
){
    Text(description, modifier = modifier)
}

@Composable
private fun Images(
    modifier: Modifier = Modifier,
    imageUrls: List<String> = listOf(),
){
    for (element in imageUrls)
        AsyncImage(
            modifier = modifier,
            model = element,
            contentDescription = ""
        )
}

@Composable
private fun ActionIcons(
    modifier: Modifier = Modifier,
){
    val painterResources = listOf(
        R.drawable.baseline_favorite_24,
        R.drawable.baseline_repeat_24,
        R.drawable.baseline_chat_bubble_24,
        R.drawable.baseline_bar_chart_24,
    )

    val contentDescriptions = listOf(
        R.string.favorite,
        R.string.repost,
        R.string.comment,
        R.string.view_count,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ){
        for(i in painterResources.indices){
            ActionIcon(
                modifier = modifier,
                painterRes = painterResources[i],
                contentRes = contentDescriptions[i],
            )
        }
    }
}

@Composable
private fun ActionIcon(
    modifier: Modifier = Modifier,
    painterRes: Int,
    contentRes: Int,
){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
            .clickable {  }
            .padding(dimensionResource(R.dimen.padding_action_icon)),
    ){
        Image(
            painter = painterResource(painterRes),
            contentDescription = stringResource(contentRes),
            colorFilter = tint(MaterialTheme.colorScheme.secondary)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_action_icon)))
        Text("0")
    }
}
