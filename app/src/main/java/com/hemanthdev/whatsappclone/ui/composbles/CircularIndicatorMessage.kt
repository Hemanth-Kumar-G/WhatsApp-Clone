package com.hemanthdev.whatsappclone.ui.composbles

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hemanthdev.whatsappclone.ui.theme.black15

/**
 * A loading progress indicator.
 *
 * This will also take a [message] to show it with the progress indicator.
 */

@Composable
fun CircularIndicatorMessage(message: String) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(0.5f),
        elevation = 10.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp),
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                message,
                style = black15
            )
        }
    }
}