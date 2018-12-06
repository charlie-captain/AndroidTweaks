package com.charlie.androidtweaks.window

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TweakWindowService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        TweakWindowManager.showPermission(this)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        TweakWindowManager.dismissWindow()
    }


}