package com.hemanthdev.whatsappclone.modules.registration

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hemanthdev.whatsappclone.ui.composbles.CircularIndicatorMessage
import com.hemanthdev.whatsappclone.ui.composbles.SnackbarCustom
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.ui.theme.black20Bold
import com.hemanthdev.whatsappclone.ui.theme.gray15
import com.hemanthdev.whatsappclone.ui.theme.white20Bold
import kotlinx.coroutines.launch

@Composable
fun RegistrationView(
    home: () -> Unit,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()

    val loading: Boolean by registrationViewModel.loading.observeAsState(initial = false)
    val showMessage: Boolean by registrationViewModel.showMessage.observeAsState(initial = false)
    val message: String by registrationViewModel.message.observeAsState(initial = "")
    val name: String by registrationViewModel.name.observeAsState(initial = "")
    val username: String by registrationViewModel.username.observeAsState(initial = "")
    val emailId: String by registrationViewModel.emailID.observeAsState(initial = "")
    val status: String by registrationViewModel.status.observeAsState(initial = "")

    val showSnackbar = {
        coroutineScope.launch {
            when (snackbarHostState.showSnackbar(
                message = message,
            )) {
                SnackbarResult.Dismissed -> {
                    registrationViewModel.snackbarDismissed()
                }
                SnackbarResult.ActionPerformed -> {
                    registrationViewModel.snackbarDismissed()
                }
            }
        }
    }
    if (showMessage) {
        showSnackbar()
    }

    WhatsAppCloneTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 15.dp, vertical = 30.dp)
                    ) {
                        item {
                            Text(
                                text = "Account created",
                                style = black20Bold
                            )
                            Text(
                                text = "You are finally a member of our family .We still need few more details to know you better",
                                style = gray15
                            )

                            //  name
                            Spacer(modifier = Modifier.height(50.dp))
                            EntryTextField(
                                placeHolder = "Enter your Full Name",
                                label = "Name",
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                value =  name
                            ) { registrationViewModel.onNameChange(it) }

                            //  userName
                            Spacer(modifier = Modifier.height(20.dp))
                            EntryTextField(
                                placeHolder = "Enter your User Name",
                                label = "UserName",
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                value = username
                            ) { registrationViewModel.onUsernameChange(it) }

                            //  emailId
                            Spacer(modifier = Modifier.height(20.dp))
                            EntryTextField(
                                placeHolder = "Enter your personal email Id",
                                label = "Email Id",
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                value = emailId
                            ) { registrationViewModel.onEmailIdChange(it) }

                            //  status
                            Spacer(modifier = Modifier.height(20.dp))
                            EntryTextField(
                                placeHolder = "Enter a status/bio to tell a bit about yourself",
                                label = "status",
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Sentences
                                ),
                                value = status
                            ) { registrationViewModel.onStatusChange(it) }
                            Spacer(modifier = Modifier.height(50.dp))

                            Button(modifier = Modifier
                                .fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp),
                                onClick = {
                                    focusManager.clearFocus()
                                    focusManager.clearFocus()
                                    if (context is Activity) {
                                        registrationViewModel.updateUserDetails(home)
                                    }
                                }) {
                                Text(
                                    "Continue",
                                    style = white20Bold,
                                )
                            }

                        }

                    }
                    if (loading) {
                        CircularIndicatorMessage(message = "Please Wait")
                    }
                }
                SnackbarCustom(snackbarHostState)
            }
        }
    }
}

@Composable
fun EntryTextField(
    placeHolder: String,
    label: String,
    keyboardOptions: KeyboardOptions,
    value: String,
    singleLine: Boolean = true,
    textFieldValue: (String) -> Unit

) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        textStyle = black20Bold,
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        onValueChange = { textFieldValue(it) },
        placeholder = {
            Text(
                placeHolder,
                style = gray15
            )
        },
        label = {
            Text(
                label,
                style = gray15
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.LightGray,
            backgroundColor = Color.White
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}
