package com.hemanthdev.whatsappclone.modules.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.utils.*
import com.hemanthdev.whatsappclone.utils.Common.defaultPic
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.farhanroy.cccp.CCPCountry
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val phoneAuthUtility: PhoneAuthUtility,
    private val utility: Utility,
    private val firestoreUtility: FirestoreUtility
) : ViewModel() {


    //Message to be shown to the user.
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    // Is there a message to be shown to the user.
    private val _showMessage = MutableLiveData(false)
    val showMessage: LiveData<Boolean> = _showMessage

    // Is a loader need to be shown to the user.
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    // Is otp is sent to the number.
    private val _otpSent = MutableLiveData(false)
    val otpSent: LiveData<Boolean> = _otpSent

    // Phone number entered by the user.
    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> = _phoneNumber

    // OTP entered by the user
    private val _otp = MutableLiveData("")
    val otp: LiveData<String> = _otp

    var storedVerificationId: String? = null

    var country: CCPCountry? = null

    /**
     * Update show message and value when the snack bar is dismissed.
     *
     * This is required to let the compose know that the snackbar is being dismissed so when
     * next time the snackbar needs to be re-build it will do that instead of not re-building it
     * since the value has not changed.
     */
    fun snackbarDismissed() {
        _showMessage.value = false
        _message.value = ""
    }

    /**
     * Whenever there is a change in the text field for OTP, then this method will
     * be used to update the value and the UI state.
     */
    fun onOTPChange(value: String) {
        _otp.value = value
    }

    /** Whenever there is a change in the text field for phone number, then this method will
     * be used to update the value and the UI state.
     */
    fun onPhoneNumberChange(value: String) {
        _phoneNumber.value = value
    }

    /**
     * Initiate the OTP for the entered number.
     * [currentActivity] is the current activity which will be given to phone number authenticator.
     */
    fun sendOTPToPhoneNumber(currentActivity: Activity?) {
        if (loading.value == true) return
        try {
            val activity = currentActivity ?: throw IllegalArgumentException()
            val code = country?.phoneCode ?: "91"
            val number = getPhoneNumber()

            phoneAuthUtility.sendOtp(
                phoneNumber = "+$code$number",
                activity = activity,
                callback = object : OtpSentCallbacks {
                    override fun onOtpInitiated() {
                        _loading.value = true
                    }

                    override fun onOtpSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken,
                        message: String
                    ) {
                        _loading.value = false
                        _otpSent.value = true
                        _showMessage.value = true
                        _message.value = message
                        storedVerificationId = verificationId
                    }

                    override fun onError(message: String) {
                        _loading.value = false
                        _showMessage.value = true
                        _message.value = message
                    }
                }
            )

        } catch (e: Exception) {
            _loading.value = false
            _showMessage.value = true
            _message.value = e.localizedMessage ?: ""
        }
    }

    /**
     * @return a non-nullable phone number
     * Throws exception
     */
    private fun getPhoneNumber(): String {
        val number = _phoneNumber.value
            ?: throw IllegalArgumentException("Please provide a valid phone number")
        if (number.isEmpty()) throw IllegalArgumentException("Please provide a valid phone number")
        return number
    }

    /**
     * Return a non-nullable OTP value
     * Throws exception
     */
    private fun getOtpCode(cCode: String, number: String): String {
        val code = _otp.value
            ?: throw IllegalArgumentException("Please enter the OTP sent to $cCode$number")

        if (code.isEmpty()) {
            throw IllegalArgumentException("Please enter the OTP sent to $cCode$number")
        }

        return code
    }

    /**
     * Start the otp verification
     * [home] is the method which will be used to redirect the user to the home page if the
     * authentication was successful
     */
    private fun startOTPVerification(
        home: () -> Unit,
        signUp: () -> Unit
    ) {
        val cCode = country?.phoneCode ?: "+91"
        val number = getPhoneNumber()
        val verificationId = storedVerificationId
            ?: throw IllegalArgumentException("Oops!! Something went wrong. Please try again.")
        val code = getOtpCode(cCode, number)
        phoneAuthUtility.verifyOTP(
            verificationId = verificationId,
            code = code,
            callback = object : VerifyOtpCallbacks {
                override fun otpVerificationInitiated() {
                    _loading.value = true
                }

                override fun onVerified(user: FirebaseUser) {
                    val userDetails = User(
                        phoneNumber = number,
                        countryName = country?.name!!,
                        countryNameCode = country?.nameCode!!,
                        countryPhoneCode = country?.phoneCode!!,
                        lastLoggedIn = utility.currentTimeStamp(),
                        appVersion = utility.applicationVersion(),
                        deviceId = utility.getDeviceId(),
                        deviceModel = utility.deviceModel(),
                        deviceOs = utility.systemOS(),
                        userId = user.uid
                    )
                    firestoreUtility.checksForUserDetails(
                        callbacks = object : FirestoreCallbacks {
                            override fun isFalse() {
                                userDetails.accountCreatedOn = utility.currentTimeStamp()
                                userDetails.profilePic = defaultPic.shuffled().first()
                                updateUserDetails(userDetails, home, signUp, true)
                            }

                            override fun userDetails(user: User) {
                                user.lastLoggedIn = utility.currentTimeStamp()
                                user.appVersion = utility.applicationVersion()
                                user.deviceId = utility.getDeviceId()
                                user.deviceModel = utility.deviceModel()
                                user.deviceOs = utility.systemOS()
                                updateUserDetails(user, home, signUp, !user.isDetailsAdded)
                            }

                            override fun onError(message: String) {
                                _loading.value = false
                                _showMessage.value = true
                                _message.value = message
                            }
                        }
                    )
                }

                override fun onError(message: String) {
                    _loading.value = false
                    _showMessage.value = true
                    _message.value = message
                }

            })
    }

    private fun updateUserDetails(
        userDetails: User,
        home: () -> Unit,
        signUp: () -> Unit,
        isNewUser: Boolean
    ) = firestoreUtility.setUserDetails(
        data = userDetails,
        callbacks = object : FirestoreCallbacks {
            override fun isTrue() {
                _loading.value = false
                if (isNewUser) signUp() else home()
            }

            override fun onError(message: String) {
                _loading.value = false
                _showMessage.value = true
                _message.value = message
            }
        }
    )


    fun verifyOTP( home: () -> Unit, signUp: () -> Unit) {
        if (loading.value == true) return
        try {
            startOTPVerification( home, signUp)
        } catch (exception: Exception) {
            _loading.value = false
            _showMessage.value = true
            _message.value = exception.localizedMessage ?: ""
        }
    }
}