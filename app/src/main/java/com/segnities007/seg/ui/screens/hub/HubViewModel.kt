package com.segnities007.seg.ui.screens.hub

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.segnities007.seg.ui.screens.login.NavigateAction
import com.segnities007.seg.ui.screens.login.NavigateState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth

@HiltViewModel
class HubViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
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


    fun checkUser(){
        val currentUser = supabaseClient.auth.currentUserOrNull()
        Log.d("checkHub", "current user is $currentUser")
    }

}