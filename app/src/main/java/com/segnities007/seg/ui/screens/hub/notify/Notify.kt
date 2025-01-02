package com.segnities007.seg.ui.screens.hub.notify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.segnities007.seg.R

@Composable
fun Notify(
    modifier: Modifier,
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        BulletinBoard()
    }
}

@Composable
private fun BulletinBoard(){
    ElevatedCard(
        modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_normal))
    ) { }
}