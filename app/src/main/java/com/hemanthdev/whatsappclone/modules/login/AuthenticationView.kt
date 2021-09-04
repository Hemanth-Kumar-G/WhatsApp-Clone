package com.hemanthdev.whatsappclone.modules.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hemanthdev.whatsappclone.ui.theme.*
import io.github.farhanroy.cccp.CountryCodeDialog

@ExperimentalAnimationApi
//@Preview(showBackground = true)
@Composable
fun AuthenticationView(signUp: () -> Unit) {

    val focusManager = LocalFocusManager.current

    val phoneNumber = remember { mutableStateOf(TextFieldValue()) }
    val continueBtnVisibility = remember {
        mutableStateOf(true)
    }
    val verifyBtnVisibility = remember {
        mutableStateOf(false)
    }
    val otpVisibility = remember {
        mutableStateOf(false)
    }


    WhatsAppCloneTheme {

        Surface(color = MaterialTheme.colors.background) {
            Scaffold() {

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
                        value = phoneNumber.value,
                        textStyle = black20Bold,
                        leadingIcon = {
                            CountryCodeDialog(
                                pickedCountry = {
                                    Log.e("CountryCodeDialog ", "${it.name} ${it.phoneCode}")
                                })
                        },
                        onValueChange = {
                            phoneNumber.value = it
                        },
                        placeholder = {
                            Text(
                                "9845xxxxxxxx",
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

                    Otp(visibility = otpVisibility.value)

                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonCompose(
                        text = "Continue",
                        visibility = continueBtnVisibility.value
                    ) {
                        otpVisibility.value = true
                        verifyBtnVisibility.value = true
                        continueBtnVisibility.value = false
                    }
                    ButtonCompose(
                        text = "Verify",
                        visibility = verifyBtnVisibility.value
                    ) {
                        signUp()
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Otp(
    visibility: Boolean
) {
    AnimatedVisibility(visible = visibility) {


        val otp = remember { mutableStateOf(TextFieldValue()) }
        val maxChar = 4
        val focusManager = LocalFocusManager.current

        Column(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "Insert the OTP to verify",
                style = gray15
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = otp.value,
                onValueChange = {
                    if (it.text.length <= maxChar)
                        otp.value = it
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
