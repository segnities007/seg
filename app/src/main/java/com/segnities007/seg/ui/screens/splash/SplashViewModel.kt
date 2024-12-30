package com.segnities007.seg.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
): ViewModel(){
    fun hasLogged(
        navController: NavHostController,
    ){
        Log.d("check splash", "run hasLogged")
        viewModelScope.launch{
            withContext(Dispatchers.Main){
                authRepositoryImpl.hasLogged(navController)
            }
        }
    }
}
