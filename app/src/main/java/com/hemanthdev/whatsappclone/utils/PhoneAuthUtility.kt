package com.hemanthdev.whatsappclone.utils

import android.app.Activity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

class PhoneAuthUtility @Inject constructor() {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        callback: OtpSentCallbacks
    ) {
        callback.onOtpInitiated()
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) = Unit

            override fun onVerificationFailed(exception: FirebaseException) {
                callback.onError(exception.localizedMessage ?: "")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                callback.onOtpSent(
                    verificationId = verificationId,
                    token = token,
                    message = "An Otp has been sent to $phoneNumber"
                )
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) = Unit
        }
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun verifyOTP(
        verificationId: String,
        code: String,
        callback: VerifyOtpCallbacks
    ) {
        callback.otpVerificationInitiated()

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        callback.onVerified(user = user)
                    } else {
                        callback.onError(message = "")
                    }
                } else {
                    callback.onError(message = task.exception?.localizedMessage ?: "")
                }
            }
    }
}
