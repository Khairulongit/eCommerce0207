package com.inthenameofallah.ecommerce

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService: Service() {

    override fun onCreate() {

        Log.wtf("inside","On Create")
    }





    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet Implemet")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }



//    fun isOnline()
}