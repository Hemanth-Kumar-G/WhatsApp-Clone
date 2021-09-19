package com.hemanthdev.whatsappclone.modules.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hemanthdev.whatsappclone.utils.FirestoreUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseUtility: FirestoreUtility
) : ViewModel() {

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private val _showMessage = MutableLiveData(false)
    val showMessage: LiveData<Boolean> = _showMessage

    fun snackbarDismissed() {
        _showMessage.value = false
        _message.value = ""
    }

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading


    fun signOut(splash: () -> Unit) {
        _loading.value = true
        firebaseUtility.signOut()
        _loading.value = false
        splash()
    }
}