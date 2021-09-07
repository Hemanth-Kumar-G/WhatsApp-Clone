package com.hemanthdev.whatsappclone.ui.composbles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hemanthdev.whatsappclone.ui.theme.actionBold
import com.hemanthdev.whatsappclone.ui.theme.black20Bold


@Composable
fun SnackbarCustom(snackbarHostState: SnackbarHostState) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (snackbarHostRef) = createRefs()

        SnackbarHost(
            modifier = Modifier
                .constrainAs(snackbarHostRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    backgroundColor = Color.White,
                    action = {
                        Text(
                            text = "okay",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                },
                            style = actionBold
                        )
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text =
                        if ((snackbarHostState.currentSnackbarData?.message ?: "").isEmpty())
                           "Oops! Something when Wrong"
                        else
                            snackbarHostState.currentSnackbarData!!.message,
                        style = black20Bold
                    )
                }
            },
        )
    }
}