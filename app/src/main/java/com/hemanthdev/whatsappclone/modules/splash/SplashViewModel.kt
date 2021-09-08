package com.hemanthdev.whatsappclone.modules.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.utils.FirestoreCallbacks
import com.hemanthdev.whatsappclone.utils.FirestoreUtility
import com.hemanthdev.whatsappclone.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firestoreUtility: FirestoreUtility,
    private val utility: Utility
) : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun checkIfUserLoggedIn(
        home: () -> Unit,
        login: () -> Unit,
        registration: () -> Unit
    ) {
        if (firebaseAuth.currentUser != null) {
            firestoreUtility.checksForUserDetails(
                callbacks = object : FirestoreCallbacks {
                    override fun isFalse() {
                        home()
                    }

                    override fun userDetails(user: User) {
                        user.lastLoggedIn = utility.currentTimeStamp
                        user.appVersion = utility.applicationVersion
                        user.deviceId = utility.deviceId
                        user.deviceModel = utility.deviceModel
                        user.deviceOs = utility.systemOS

                        updateUserDetails(user, home, registration, user.isDetailsAdded)
                    }

                    override fun onError(message: String) {
                        home()
                    }
                }
            )
        } else {
            login()
        }
    }

    private fun updateUserDetails(
        user: User,
        home: () -> Unit,
        registration: () -> Unit,
        isUserDetailsAdded: Boolean
    ) = firestoreUtility.setUserDetails(
        data = user,
        callbacks = object : FirestoreCallbacks {
            override fun isTrue() {
                navigateTo(home, registration, isUserDetailsAdded)
            }

            override fun onError(message: String) {
                navigateTo(home, registration, isUserDetailsAdded)
            }
        }
    )

    private fun navigateTo(home: () -> Unit, formFill: () -> Unit, isUserDetailsAdded: Boolean) {
        if (isUserDetailsAdded) {
            home()
        } else {
            formFill()
        }
    }

}

