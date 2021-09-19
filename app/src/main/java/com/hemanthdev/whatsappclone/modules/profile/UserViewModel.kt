package com.hemanthdev.whatsappclone.modules.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.utils.FirestoreCallbacks
import com.hemanthdev.whatsappclone.utils.FirestoreUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val firestoreUtility: FirestoreUtility
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

    private val _userDetails = MutableLiveData(User())
    val userDetails: LiveData<User> = _userDetails

    fun getUserDetails() {
        _loading.value = true
        firestoreUtility.userDetailsListener(
            callbacks = object : FirestoreCallbacks {
                override fun userDetails(user: User) {
                    _loading.value = false
                    _userDetails.value = user
                }

                override fun onError(message: String) {
                    _loading.value = false
                    _showMessage.value = true
                    _message.value = message
                }
            }
        )
    }
}