package com.hemanthdev.whatsappclone

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize firebase at the starting
        FirebaseApp.initializeApp(this)

    }
}