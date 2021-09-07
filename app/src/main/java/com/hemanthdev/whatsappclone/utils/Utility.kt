package com.hemanthdev.whatsappclone.utils

import android.os.Build
import com.hemanthdev.whatsappclone.BuildConfig
import javax.inject.Inject

class Utility @Inject constructor() {

    /**
     * Get current time stamp
     */
    fun currentTimeStamp(): Long {
        return System.currentTimeMillis()
    }

    /**
     * Get application version
     */
    fun applicationVersion(): String {
        return "${BuildConfig.VERSION_NAME}:${BuildConfig.VERSION_CODE}"
    }

    /**
     * Get device id
     */
    fun getDeviceId(): String {
        return Build.ID
    }

    /**
     * Get device model
     */
    fun deviceModel(): String {
        return "${Build.MODEL} ${Build.BRAND} ${Build.DEVICE}"
    }

    /**
     * Get phone OS
     */
    fun systemOS(): String {
        return "${Build.ID} ${Build.VERSION.SDK_INT} ${Build.VERSION.CODENAME}"
    }

}
