package com.hemanthdev.whatsappclone.modules.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.ui.composbles.SnackbarCustom
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.ui.theme.black20Bold
import kotlinx.coroutines.launch


@Composable
fun ProfileView(
    back: () -> Unit,
    splash: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()

    val loading: Boolean by profileViewModel.loading.observeAsState(initial = false)
    val showMessage: Boolean by profileViewModel.showMessage.observeAsState(initial = false)
    val message: String by profileViewModel.message.observeAsState(initial = "")

    /**
     * States from user view model
     */
    val loadingUser: Boolean by userViewModel.loading.observeAsState(initial = false)
    val showMessageUser: Boolean by userViewModel.showMessage.observeAsState(initial = false)
    val messageUser: String by userViewModel.message.observeAsState(initial = "")
    val userDetails: User by userViewModel.userDetails.observeAsState(initial = User())

    userViewModel.getUserDetails()
    /**
     * Show snackbar
     */
    val showSnackbar = { messageData: String ->

        coroutineScope.launch {
            when (snackbarHostState.showSnackbar(
                message = messageData
            )) {
                SnackbarResult.Dismissed -> {
                    profileViewModel.snackbarDismissed()
                }
                SnackbarResult.ActionPerformed -> {
                    profileViewModel.snackbarDismissed()
                }
            }
        }
    }

    if (showMessage) {
        showSnackbar(message)
    }
    if (showMessageUser) {
        showSnackbar(messageUser)
    }
    WhatsAppCloneTheme {

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    IconButton(onClick = {
                        back()
                    }) {
                        Icon(
                            modifier = Modifier.weight(.1f, fill = true),
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        modifier = Modifier.weight(.8f, fill = true),
                        text = "Profile",
                        style = black20Bold,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Gray)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f, true),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "profile",
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("User Name :")
                        }
                        append(userDetails.name.toUpperCase())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("email:")
                        }

                        append(userDetails.emailId)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Status :")
                        }
                        append(userDetails.status)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = { profileViewModel.signOut(splash = splash) },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "SignOut")
                }
            }
            SnackbarCustom(snackbarHostState)
        }
    }
}
