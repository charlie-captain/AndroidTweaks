package com.charlie.record.phoneyin.sample

import android.app.Application
import android.util.Log
import com.charlie.androidtweaks.core.TweakManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Utils.isDebug(this)) {
            Log.d("debug", "debug true")
            TweakManager.with(this).setPersistent(true).setShakeEnable(true).init(ExampleTweakLibrary)
        }
    }


    override fun onTerminate() {
        super.onTerminate()
        TweakManager.destroy(this)
    }

}