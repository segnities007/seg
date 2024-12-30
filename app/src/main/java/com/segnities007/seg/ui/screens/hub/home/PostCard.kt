package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.segnities007.seg.R

@Preview
@Composable
fun PostCard(
    modifier: Modifier = Modifier
        .padding(dimensionResource(R.dimen.padding_normal)),
    url: String = "https://avatars.githubusercontent.com/u/174174755?v=4",
){
    Row(
        modifier = Modifier
            .clickable {  }
            .fillMaxWidth(),
    ){
        AsyncImage(
            model = url,
            contentDescription = url,
        )
        Column(
            horizontalAlignment = Alignment.Start,
        ){
            Name(modifier = modifier)
            Description(modifier = modifier)
            Images(modifier = modifier)
            ActionIcons(modifier = modifier)
        }
    }
}

@Composable
private fun Name(
    modifier: Modifier = Modifier,
    name: String = stringResource(R.string.no_name),
){
    Text(name, modifier = modifier)
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
        modifier = modifier,
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
        modifier = modifier,
    ){
        Image(
            painter = painterResource(painterRes),
            contentDescription = stringResource(contentRes),
            colorFilter = tint(MaterialTheme.colorScheme.secondary)
        )
        Text("a")
    }
}
