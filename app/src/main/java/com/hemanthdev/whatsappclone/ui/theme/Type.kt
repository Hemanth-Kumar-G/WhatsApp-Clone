package com.hemanthdev.whatsappclone.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val black20Bold = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 20.sp
)

val gray15 = TextStyle(
    color = Color.Gray,
    fontSize = 15.sp,
)
val gray20 = TextStyle(
    color = Color.Gray,
    fontSize = 20.sp,
)

val white20Bold = TextStyle(
    color = Color.White,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold
)

val black15Bold = TextStyle(
    color = Color.Black,
    fontSize = 15.sp,
    fontWeight = FontWeight.Bold,
)

val actionBold = TextStyle(
    fontWeight = FontWeight.Bold,
    color = LightGreen
)

val black15 = TextStyle(
    color = Color.Black,
    fontSize = 15.sp
)