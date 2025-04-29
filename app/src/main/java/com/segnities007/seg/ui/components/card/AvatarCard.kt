package com.segnities007.seg.ui.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.example.domain.model.User
import com.segnities007.seg.R

@Composable
fun AvatarCard(
    modifier: Modifier = Modifier,
    user: User,
    onCardClick: () -> Unit,
) {
    ElevatedCard(
        modifier =
            Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_smaller),
                    end = dimensionResource(R.dimen.padding_small),
                ),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
    ) {
        Row(
            modifier =
                Modifier
                    .clickable { onCardClick() }
                    .padding(dimensionResource(R.dimen.padding_sn))
                    .fillMaxWidth(),
        ) {
            AsyncImage(
                modifier =
                    Modifier
                        .size(dimensionResource(R.dimen.icon_small))
                        .clip(CircleShape),
                model = user.iconURL,
                placeholder = painterResource(R.mipmap.segnities007),
                contentDescription = user.iconURL,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            Name(modifier = modifier, user = user)
        }
    }
}

@Composable
private fun Name(
    modifier: Modifier = Modifier,
    user: User,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(user.name)
        Text("@${user.userID}", color = MaterialTheme.colorScheme.secondaryContainer)
    }
}
