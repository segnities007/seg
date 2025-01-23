package com.segnities007.seg.ui.screens.login.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.segnities007.seg.R
import com.segnities007.seg.ui.screens.login.ConfirmEmailUiAction

@Composable
fun ConfirmEmail(
    modifier: Modifier = Modifier,
    onNavigateToCreateAccount: () -> Unit,
    confirmEmailUiAction: ConfirmEmailUiAction,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ElevatedCard(modifier = modifier.padding(dimensionResource(R.dimen.padding_normal))) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
                    text = stringResource(R.string.confirm_prompt_message),
                )
                ElevatedButton(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
                    onClick = { confirmEmailUiAction.onConfirmEmail(onNavigateToCreateAccount) },
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            }
        }
    }
}
