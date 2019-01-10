package com.charlie.androidtweaks.window

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_IS_KEY
import com.charlie.androidtweaks.utils.TweakValueDelegate

class TweakLifeCyclerCallback : Application.ActivityLifecycleCallbacks {

    var count = 0

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
        count++
        if (count == 1) {
            val isFloat by TweakValueDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
            if (isFloat) {
                activity?.startService(Intent(activity, TweakWindowService::class.java))
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        count--
        if (count == 0) {
            TweakWindowManager.saveLayoutParams()
            activity?.stopService(Intent(activity, TweakWindowService::class.java))
        }
    }
}