package com.charlie.example.sample

import android.app.Application
import android.util.Log
import com.charlie.androidtweaks.core.TweakManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Utils.isDebug(this)) {
            Log.d("debug", "debug true")
            TweakManager.with(this).setLibrary(ExampleTweakLibrary).setPersistent(true).setShakeEnable(true)
                .init()
        }
    }


    override fun onTerminate() {
        super.onTerminate()
        TweakManager.destroy(this)
    }

}