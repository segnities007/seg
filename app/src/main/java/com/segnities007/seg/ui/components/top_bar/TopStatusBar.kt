package com.segnities007.seg.ui.components.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.segnities007.seg.R
import com.segnities007.seg.data.model.User
import com.segnities007.seg.ui.screens.hub.SettingUiState

@Composable
fun TopStatusBar(
    user: User,
    commonPadding: Dp = dimensionResource(R.dimen.padding_moderate),
    url: String = "https://avatars.githubusercontent.com/u/174174755?v=4",
){

    Column(
        modifier = Modifier
            .shadow(
                elevation = dimensionResource(R.dimen.elevation_normal),
                shape = RoundedCornerShape(
                    bottomStart = dimensionResource(R.dimen.padding_large),
                    bottomEnd = dimensionResource(R.dimen.padding_large),
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = dimensionResource(R.dimen.padding_large),
                    bottomEnd = dimensionResource(R.dimen.padding_large),
                )
            )
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)))
        AsyncImage(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)).clip(CircleShape),
            model = url,
            contentDescription = "TODO",
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        Status(user = user)
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)))
    }

}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    commonPadding: Dp = dimensionResource(R.dimen.padding_moderate),
    user: User,
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ){
        Row(){
            Text(text = user.name)
            Spacer(modifier = Modifier.padding(commonPadding))
            Text(text = user.userID)
        }
        Row(){
            Text(text = "Follow: ${user.followCount}")
            Spacer(modifier = Modifier.padding(commonPadding))
            Text(text = "Follower: ${user.followerCount}")
        }
    }
}