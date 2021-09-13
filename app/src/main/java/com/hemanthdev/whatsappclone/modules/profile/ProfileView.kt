package com.hemanthdev.whatsappclone.modules.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.ui.theme.black20Bold


@Composable
fun ProfileView(back: () -> Unit) {

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
                        append("HEMANTH kUMAR G")
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

                        append("XYZ@gmail.com")
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
                        append("this is new status ")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "SignOut")
                }
            }
        }
    }
}
