package com.hemanthdev.whatsappclone.modules.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hemanthdev.whatsappclone.ui.theme.*

@Composable
fun RegistrationView(home: () -> Unit) {

    val nameField = remember { mutableStateOf(TextFieldValue()) }
    val userNameField = remember { mutableStateOf(TextFieldValue()) }
    val emailIdField = remember { mutableStateOf(TextFieldValue()) }
    val statusField = remember { mutableStateOf(TextFieldValue()) }

    val focusManager = LocalFocusManager.current

    WhatsAppCloneTheme {
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
                    nameField.value
                ) { nameField.value = it }

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
                    userNameField.value
                ) { userNameField.value = it }

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
                    emailIdField.value
                ) { emailIdField.value = it }

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
                    statusField.value
                ) { statusField.value = it }
                Spacer(modifier = Modifier.height(50.dp))

                Button(modifier = Modifier
                    .fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    onClick = {
                        focusManager.clearFocus()
                        home()
                    }) {
                    Text(
                        "Continue",
                        style = white20Bold,
                    )
                }

            }
        }
    }
}

@Composable
fun EntryTextField(
    placeHolder: String,
    label: String,
    keyboardOptions: KeyboardOptions,
    value: TextFieldValue,
    singleLine: Boolean = true,
    textFieldValue: (TextFieldValue) -> Unit

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
