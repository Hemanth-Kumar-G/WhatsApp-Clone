package com.hemanthdev.whatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hemanthdev.whatsappclone.modules.splash.SplashScreen
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
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
                NavHost(
                    navController = navController,
                    startDestination = SPLASH_SCREEN
                ) {
                    composable(SPLASH_SCREEN) {
                        SplashScreen()
                    }
                }
            }
        }
    }
}