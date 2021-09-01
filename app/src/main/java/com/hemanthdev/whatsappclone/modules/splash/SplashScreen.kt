package com.hemanthdev.whatsappclone.modules.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.ui.theme.splashColor


@ExperimentalUnitApi
@Preview(showBackground = true)
@Composable
fun SplashScreen(
) {
    WhatsAppCloneTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(splashColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 14.sp
                        )
                    ) {
                        append("Welcome to")
                    }
                    append("\n")
                    append("WhatsApp")
                },
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(letterSpacing = TextUnit(.2f, TextUnitType.Em)),

            )
        }
    }
}