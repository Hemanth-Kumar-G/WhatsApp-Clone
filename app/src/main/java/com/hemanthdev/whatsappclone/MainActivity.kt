package com.hemanthdev.whatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hemanthdev.whatsappclone.modules.splash.SplashScreen
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.utils.Action
import com.hemanthdev.whatsappclone.utils.LOGIN_SCREEN
import com.hemanthdev.whatsappclone.utils.SPLASH_SCREEN
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WhatsAppCloneTheme {
                val navController = rememberNavController()
                val actions = remember(navController) { Action(navController) }
                NavHost(
                    navController = navController,
                    startDestination = SPLASH_SCREEN
                ) {
                    composable(route = SPLASH_SCREEN) {
                        SplashScreen(
                            login = actions.login
                        )
                    }
                    composable(route = LOGIN_SCREEN) {
                        Box(
                            modifier = Modifier
                                .background(Color.Red)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}