package com.hemanthdev.whatsappclone.modules.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.utils.FirestoreCallbacks
import com.hemanthdev.whatsappclone.utils.FirestoreUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
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

    private val _users = MutableLiveData(listOf<User>())
    val users: LiveData<List<User>> = _users

    fun getUsers() {
        firebaseUtility.allUsers(
            callbacks = object : FirestoreCallbacks {
                override fun userList(users: List<User>) {
                    _users.value = users
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