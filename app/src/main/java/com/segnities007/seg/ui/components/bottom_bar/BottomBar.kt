package com.segnities007.seg.ui.components.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.segnities007.seg.domain.model.BottomBarItem

@Composable
fun BottomBar(
    items: BottomBarItem,
    currentIndex: Int,
    onClick: (Int) -> Unit,
){

    NavigationBar{
        items.labels.forEachIndexed { index, label ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if(currentIndex == index)
                                        painterResource(items.selectedIconIDs[index]) else
                                        painterResource(items.unSelectedIconIDs[index]),
                        contentDescription = items.labels[index],
                    )
                },
                label = { Text(items.labels[index]) },
                selected = currentIndex == index,
                onClick = {onClick(index)},
            )
        }
    }

}