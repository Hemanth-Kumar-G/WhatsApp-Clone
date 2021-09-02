package com.hemanthdev.whatsappclone.modules.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SplashViewModel"
@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    fun launchNext(login: () -> Unit, duration: Long = 6000) {
        viewModelScope.launch(Dispatchers.Default) {
            delay(duration)
            viewModelScope.launch(Dispatchers.Main) {
                login()
            }
        }
    }
}