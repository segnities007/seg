package com.segnities007.seg.ui.screens.hub

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.segnities007.seg.domain.presentation.TopLayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.segnities007.seg.ui.screens.login.NavigateAction
import com.segnities007.seg.ui.screens.login.NavigateState
import io.github.jan.supabase.SupabaseClient

data class PostUiState(
    val inputText: String = ""
)

data class PostUiAction(
    val onInputTextChange: (newInputText: String) -> Unit
)

@HiltViewModel
class HubViewModel @Inject constructor(
) : TopLayerViewModel() {

    var navigateState by mutableStateOf(NavigateState())
        private set

    var postUiState by mutableStateOf(PostUiState())
        private set

    fun getPostUiAction(): PostUiAction{
        return PostUiAction(
            onInputTextChange = this::onInputTextChange
        )
    }

    fun getNavigateAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    private fun onInputTextChange(newInputText: String){
        postUiState = postUiState.copy(inputText = newInputText)
    }

    private fun onIndexChange(newIndex: Int){
        navigateState = navigateState.copy(index = newIndex)
    }

}