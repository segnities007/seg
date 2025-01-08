package com.segnities007.seg.ui.components.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.segnities007.seg.domain.model.BottomBarItem
import com.segnities007.seg.domain.model.NavigationIndex

@Composable
fun BottomBar(
    items: BottomBarItem,
    indices: List<NavigationIndex>,
    currentIndex: NavigationIndex,
    onClick: (NavigationIndex) -> Unit,
){

    NavigationBar{
        items.labels.forEachIndexed { index, label ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if(currentIndex == indices[index])
                                        painterResource(items.selectedIconIDs[index]) else
                                        painterResource(items.unSelectedIconIDs[index]),
                        contentDescription = label,
                    )
                },
                label = { Text(items.labels[index]) },
                selected = currentIndex == indices[index],
                onClick = {onClick(indices[index])},
            )
        }
    }

}