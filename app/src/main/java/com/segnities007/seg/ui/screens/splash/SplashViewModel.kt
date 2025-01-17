package com.segnities007.seg.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segnities007.seg.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val authRepositoryImpl: AuthRepositoryImpl,
    ) : ViewModel() {
        fun hasLogged(
            onNavigateToLogin: () -> Unit,
            onNavigateToHost: () -> Unit,
        ) {
            Log.d("check splash", "run hasLogged")
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    authRepositoryImpl.hasLogged(onNavigateToHost = onNavigateToHost, onNavigateToLogin = onNavigateToLogin)
                }
            }
        }
    }
