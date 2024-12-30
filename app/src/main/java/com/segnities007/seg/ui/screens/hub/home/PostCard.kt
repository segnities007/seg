package com.segnities007.seg.ui.screens.hub.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    url: String = "https://avatars.githubusercontent.com/u/174174755?v=4",

){
    Row(
        modifier = Modifier.fillMaxWidth(),
    ){
        AsyncImage(
            model = url,
            contentDescription = url,
        )
        Column(
            horizontalAlignment = Alignment.Start
        ){
            Name()
            Description()
            Images()
            ActionIcons()
        }
    }
}

@Composable
private fun Name(
    name: String,

){

}

@Composable
private fun Description(){

}

@Composable
private fun Images(){

}

@Composable
private fun ActionIcons(){

}