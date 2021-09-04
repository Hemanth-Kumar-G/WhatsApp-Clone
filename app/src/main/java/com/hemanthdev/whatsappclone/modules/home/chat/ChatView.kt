package com.hemanthdev.whatsappclone.modules.home.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme


@Composable
fun ChatView() {
    WhatsAppCloneTheme() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red))
    }

}