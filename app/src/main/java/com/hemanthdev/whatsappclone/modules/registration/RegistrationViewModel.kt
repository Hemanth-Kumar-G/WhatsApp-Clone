package com.hemanthdev.whatsappclone.modules.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.utils.FirestoreCallbacks
import com.hemanthdev.whatsappclone.utils.FirestoreUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val firestoreUtility: FirestoreUtility
) : ViewModel() {

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private val _showMessage = MutableLiveData(false)
    val showMessage: LiveData<Boolean> = _showMessage

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(value: String) {
        _name.value = value
    }

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    private val _emailID = MutableLiveData("")
    val emailID: LiveData<String> = _emailID

    fun onEmailIdChange(value: String) {
        _emailID.value = value
    }

    private val _status = MutableLiveData("")
    val status: LiveData<String> = _status

    fun onStatusChange(value: String) {
        _status.value = value
    }

    fun snackbarDismissed() {
        _showMessage.value = false
        _message.value = ""
    }


    fun updateUserDetails(home: () -> Unit) {
        if (loading.value == true) return
        try {
            _loading.value = true
            val name = getName()
            val username = getUsername()
            val emailId = getEmailId()
            val status = getStatus()

            firestoreUtility.checksForUserDetails(
                callbacks = object : FirestoreCallbacks {
                    override fun isFalse() {
                        _loading.value = false
                        _showMessage.value = true
                        _message.value = "Oops ! something went worng"
                    }

                    override fun userDetails(user: User) {
                        user.name = name
                        user.userName = username
                        user.emailId = emailId
                        user.status = status
                        user.isDetailsAdded = true

                        updateUserDetail(user, home)
                    }

                    override fun onError(message: String) {
                        _loading.value = false
                        _showMessage.value = true
                        _message.value = message
                    }
                }
            )
        } catch (exception: Exception) {
            _loading.value = false
            _showMessage.value = true
            _message.value = exception.localizedMessage ?: ""
        }
    }

    private fun getName(): String {
        val name = _name.value
            ?: throw IllegalArgumentException("Please provide an name address")
        if (name.isEmpty()) {
            throw IllegalArgumentException("Please provide an name address")
        }
        return name
    }

    private fun getUsername(): String {
        val username = _username.value
            ?: throw IllegalArgumentException("Please provide an userName address")
        if (username.isEmpty()) {
            throw IllegalArgumentException("Please provide an userName address")
        }
        return username
    }

    private fun getEmailId(): String {
        val emailID = _emailID.value
            ?: throw IllegalArgumentException("Please provide an email address")
        if (emailID.isEmpty()) {
            throw IllegalArgumentException("Please provide an email address")
        }
        return emailID
    }

    private fun getStatus(): String {
        val status = _status.value
            ?: throw IllegalArgumentException("Please provide  a status")
        if (status.isEmpty()) {
            throw IllegalArgumentException("Please provide  a status")
        }
        return status
    }

    private fun updateUserDetail(user: User, home: () -> Unit) {
        firestoreUtility.setUserDetails(
            data = user,
            callbacks = object : FirestoreCallbacks {
                override fun isTrue() {
                    _loading.value = false
                    home()
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