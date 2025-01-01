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
    val inputText: String = "",
    val selectedUris: List<String> = listOf(),
)

data class PostUiAction(
    val onInputTextChange: (newInputText: String) -> Unit,
    val onSelectedUrisChange: (newUris: List<String>) -> Unit,
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
            onInputTextChange = this::onInputTextChange,
            onSelectedUrisChange = this::onSelectedUrisChange,
        )
    }

    fun getNavigateAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    private fun onSelectedUrisChange(newUris: List<String>){
        postUiState = postUiState.copy(selectedUris = newUris)
    }

    private fun onInputTextChange(newInputText: String){
        postUiState = postUiState.copy(inputText = newInputText)
    }

    private fun onIndexChange(newIndex: Int){
        navigateState = navigateState.copy(index = newIndex)
    }

}