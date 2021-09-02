package com.hemanthdev.whatsappclone.utils

import androidx.navigation.NavHostController

/**
 * A set of destination used in the whole application
 */
const val SPLASH_SCREEN = "splash_screen"
const val LOGIN_SCREEN = "login_screen"


/**
 * Set of routes which will be passed to different composable so that
 * the routes which are required can be taken.
 */
class Action(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(SPLASH_SCREEN)
    }

    val login: () -> Unit = {
        navController.navigate(LOGIN_SCREEN) {
            popUpTo(SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }
}