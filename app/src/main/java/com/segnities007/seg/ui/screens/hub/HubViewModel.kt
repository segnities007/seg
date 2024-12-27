package com.segnities007.seg.ui.screens.hub

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class NavigateUiState(
    val index: Int = 0,
)

@HiltViewModel
class HubViewModel @Inject constructor(

) : ViewModel(){

    var navigateUiState by mutableStateOf(NavigateUiState())

    fun onIndexChange(newIndex: Int){
        navigateUiState = navigateUiState.copy(index = newIndex)
    }

}