package com.example.feature.components.bar.status_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.domain.model.User
import com.example.feature.R

@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    user: User,
) {
    StatusBarUi(
        modifier = modifier,
        user = user,
    ) {}
}

@Composable
fun StatusBarUi(
    modifier: Modifier,
    user: User,
    content: @Composable StatusBarScope.() -> Unit,
) {
    val commonPadding: Dp = dimensionResource(R.dimen.padding_small)
    val scope =
        DefaultStatusBarScope(
            user = user,
            commonPadding = commonPadding,
        )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_nl)))
        Status(user = user, commonPadding = commonPadding)
        Spacer(modifier = Modifier.padding(commonPadding))
        scope.content()
    }
}

@Composable
private fun Status(
    user: User,
    commonFontSize: TextUnit = 24.sp,
    commonPadding: Dp,
    fontColor: Color = MaterialTheme.colorScheme.primary,
) {
    val name = "${user.name}@${user.userID}"

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_large))
                .clip(CircleShape),
            model = user.iconURL,
            placeholder = painterResource(R.mipmap.segnities007),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.padding(commonPadding))
        Text(text = name, color = fontColor, fontSize = commonFontSize)
    }
}

@Composable
fun Bottom() {
    val shape = dimensionResource(R.dimen.padding_large)
    val height = dimensionResource(R.dimen.padding_sn)

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .shadow(
                    shape =
                        RoundedCornerShape(
                            bottomStart = shape,
                            bottomEnd = shape,
                        ),
                    elevation = dimensionResource(R.dimen.elevation_nl),
                )
                .clip(
                    shape =
                        RoundedCornerShape(
                            bottomStart = shape,
                            bottomEnd = shape,
                        ),
                )
                .background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Spacer(Modifier.padding(height))
    }
}
