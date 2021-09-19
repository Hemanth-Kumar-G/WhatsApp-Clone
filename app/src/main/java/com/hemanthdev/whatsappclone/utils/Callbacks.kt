package com.hemanthdev.whatsappclone.utils

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.hemanthdev.whatsappclone.data.model.User

/**
 * Callback for when OTP needs to be generated.
 */
interface OtpSentCallbacks {
    /**
     * OTP generation started.
     */
    fun onOtpInitiated()

    /**
     * OTP is sent on the specified number
     *
     * [verificationId] and [token] are the return values given when the OTP is sent.
     *
     * A custom [message] also will be returned to show the user that otp has been sent
     */
    fun onOtpSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken,
        message: String
    )

    /**
     * Return an error message whenever there is some error or exception
     *
     * [message] contains the message readable in human form
     */
    fun onError(message: String)
}


//=================================================================================================


/**
 * Callbacks which are required when the OTP is being entered by the user.
 */
interface VerifyOtpCallbacks {
    /**
     * OTP verification has started.
     */
    fun otpVerificationInitiated()

    /**
     * If OTP was valid then this is called with the [user] object
     * which contains the details of the user from the firebase.
     */
    fun onVerified(user: FirebaseUser)

    /**
     * A callback which will be used to return any error
     * [message] contains the message readable in human form
     */
    fun onError(message: String)
}

/**
 * Callbacks for Firestore
 */
interface FirestoreCallbacks {
    /**
     * Will be called if it's true for what we are looking for
     */
    fun isTrue() = Unit

    /**
     * Will be called if it's false for what we are looking for
     */
    fun isFalse() = Unit

    /**
     * Get user data
     */
    fun userDetails(user: User) = Unit

    /**
     * Get the error message
     */
    fun onError(message: String) = Unit

    /**
     * User list
     */
    fun userList(users: List<User>) {}


}
