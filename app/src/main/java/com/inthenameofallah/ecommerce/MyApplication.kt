package com.inthenameofallah.ecommerce

import android.app.Application
import com.onesignal.OneSignal
const val ONESIGNAL_APP_ID = "bdd04886-feb8-4a62-a128-482d554ba52d"


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}