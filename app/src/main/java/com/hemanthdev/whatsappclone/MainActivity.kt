package com.hemanthdev.whatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hemanthdev.whatsappclone.modules.home.HomeView
import com.hemanthdev.whatsappclone.modules.login.AuthenticationView
import com.hemanthdev.whatsappclone.modules.profile.ProfileView
import com.hemanthdev.whatsappclone.modules.registration.RegistrationView
import com.hemanthdev.whatsappclone.modules.splash.SplashScreen
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.utils.*
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @ExperimentalAnimationApi
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
                            login = actions.login,
                            home = actions.home,
                            registration = actions.registration
                        )
                    }
                    composable(route = LOGIN_SCREEN) {
                        AuthenticationView(
                            signUp = actions.registration,
                            home = actions.home
                        )
                    }
                    composable(route = REGISTRATION_SCREEN) {
                        RegistrationView(home = actions.home)
                    }
                    composable(route = HOME_SCREEN) {
                        HomeView(
                            profile = actions.profile,
                            userMessage = actions.chatUser
                            )
                    }
                    composable(route = PROFILE_SCREEN) {
                        ProfileView(
                            back = actions.back,
                            splash = actions.splash
                        )
                    }
                }
            }
        }
    }
}