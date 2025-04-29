package com.example.feature.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    fun hasLogged(
        onNavigateToLogin: () -> Unit,
        onNavigateToHost: () -> Unit,
    ) {
        Log.d("check splash", "run hasLogged")
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                authRepository.hasLogged(
                    onNavigateToHost = onNavigateToHost,
                    onNavigateToLogin = onNavigateToLogin,
                )
            }
        }
    }
}
