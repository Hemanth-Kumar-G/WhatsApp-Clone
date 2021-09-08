package com.hemanthdev.whatsappclone.utils

import android.os.Build
import com.hemanthdev.whatsappclone.BuildConfig
import javax.inject.Inject

class Utility @Inject constructor() {

    val currentTimeStamp
        get() = System.currentTimeMillis()

    val applicationVersion
        get() = "${BuildConfig.VERSION_NAME}:${BuildConfig.VERSION_CODE}"

    val deviceId: String
        get() = Build.ID

    val deviceModel: String
        get() = "${Build.MODEL} ${Build.BRAND} ${Build.DEVICE}"

    val systemOS: String
        get() = "${Build.ID} ${Build.VERSION.SDK_INT} ${Build.VERSION.CODENAME}"

}
