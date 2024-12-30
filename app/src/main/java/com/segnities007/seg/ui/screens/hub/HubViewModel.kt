package com.segnities007.seg.ui.screens.hub

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.segnities007.seg.ui.screens.login.NavigateAction
import com.segnities007.seg.ui.screens.login.NavigateState

@HiltViewModel
class HubViewModel @Inject constructor(

) : ViewModel(){

    var navigateState by mutableStateOf(NavigateState())

    fun getNavigateAction(): NavigateAction{
        return NavigateAction(
            onIndexChange = this::onIndexChange
        )
    }

    private fun onIndexChange(newIndex: Int){
        navigateState = navigateState.copy(index = newIndex)
    }

    private fun onPickerImages(){
//        val pickMultipleMedia =
//            rememberLauncherForActivityResult(PickMultipleVisualMedia(5)) { uris ->
//                // Callback is invoked after the user selects media items or closes the
//                // photo picker.
//                if (uris.isNotEmpty()) {
//                    Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
//                } else {
//                    Log.d("PhotoPicker", "No media selected")
//                }
//            }
    }

}