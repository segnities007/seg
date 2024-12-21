package com.segnities007.seg.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegHeader(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
){

    CenterAlignedTopAppBar(
        title = { TitleUI() },
        navigationIcon = { NavigationIcon() },
        actions = { ActionIcon() },
        scrollBehavior = scrollBehavior,
    )

}

@Composable
private fun TitleUI(){
    Text("Centered Top App Bar")
}

@Composable
private fun NavigationIcon(){
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Localized description"
        )
    }
}

@Composable
private fun ActionIcon(){
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Localized description"
        )
    }
}