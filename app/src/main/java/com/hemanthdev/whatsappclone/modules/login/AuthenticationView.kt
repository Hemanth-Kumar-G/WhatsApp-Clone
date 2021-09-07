package com.hemanthdev.whatsappclone.modules.login

import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hemanthdev.whatsappclone.ui.composbles.CircularIndicatorMessage
import com.hemanthdev.whatsappclone.ui.composbles.SnackbarCustom
import com.hemanthdev.whatsappclone.ui.theme.*
import io.github.farhanroy.cccp.CountryCodeDialog
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun AuthenticationView(
    signUp: () -> Unit,
    home: () -> Unit,
    authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()

    val loading: Boolean by authenticationViewModel.loading.observeAsState(initial = false)
    val showMessage: Boolean by authenticationViewModel.showMessage.observeAsState(initial = false)
    val otpSent: Boolean by authenticationViewModel.otpSent.observeAsState(initial = false)
    val message: String by authenticationViewModel.message.observeAsState(initial = "")
    val phoneNumber: String by authenticationViewModel.phoneNumber.observeAsState(initial = "")


    val showSnackbar = {
        coroutineScope.launch {
            when (snackbarHostState.showSnackbar(
                message = message
            )) {
                SnackbarResult.ActionPerformed -> {
                    authenticationViewModel.snackbarDismissed()
                }
                SnackbarResult.Dismissed -> {
                    authenticationViewModel.snackbarDismissed()
                }
            }
        }
    }

    if (showMessage) {
        Log.e("TAG", "AuthenticationView: $message")
        showSnackbar()
    }

    WhatsAppCloneTheme {

        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 15.dp, vertical = 30.dp)
                    ) {
                        Text(
                            text = "Welcome to Whatsapp",
                            textAlign = TextAlign.Center,
                            style = black20Bold
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "Enter your phone number to continue",
                            textAlign = TextAlign.Center,
                            style = gray15
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            value = phoneNumber,
                            textStyle = black20Bold,
                            leadingIcon = {
                                CountryCodeDialog(
                                    pickedCountry = {
                                        authenticationViewModel.country = it
                                        Log.e("CountryCodeDialog ", "${it.name} ${it.phoneCode}")
                                    })
                            },
                            onValueChange = {
                                authenticationViewModel.onPhoneNumberChange(it)
                            },
                            placeholder = {
                                Text(
                                    "98XXXXXXXX",
                                    style = gray20
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                unfocusedIndicatorColor = Color.Gray
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Otp(home = home, signUp = signUp)

                        Spacer(modifier = Modifier.height(20.dp))
                        ButtonCompose(
                            text = "Continue",
                            visibility = otpSent.not()
                        ) {
                            if (context is Activity) {
                                authenticationViewModel.sendOTPToPhoneNumber(context)
                            }
                        }

                        ButtonCompose(
                            text = "Verify",
                            visibility = otpSent
                        ) {
                            authenticationViewModel.verifyOTP(home, signUp)
                        }
                    }
                    if (loading) {
                        CircularIndicatorMessage(
                            message = "Please Wait"
                        )
                    }
                    SnackbarCustom(snackbarHostState)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Otp(
    home: () -> Unit,
    signUp: () -> Unit,
    authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current

    val otpSent: Boolean by authenticationViewModel.otpSent.observeAsState(initial = false)
    val otp: String by authenticationViewModel.otp.observeAsState(initial = "")

    AnimatedVisibility(visible = otpSent) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "Insert the OTP to verify",
                style = gray15
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = otp,
                onValueChange = {
                    authenticationViewModel.onOTPChange(it)
                },
                textStyle = black20Bold,
                singleLine = true,
                placeholder = {
                    Text(
                        text = "X X X X X X",
                        style = gray20
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        authenticationViewModel.verifyOTP(home, signUp)
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    disabledBorderColor = Color.Gray
                )
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ButtonCompose(
    modifier: Modifier = Modifier,
    text: String,
    visibility: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(visible = visibility) {

        Button(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = { onClick() }) {
            Text(
                text = text,
                modifier = modifier,
                style = white20Bold
            )
        }
    }

}
