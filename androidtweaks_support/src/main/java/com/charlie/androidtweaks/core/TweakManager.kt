package com.charlie.androidtweaks.core

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.charlie.androidtweaks.shake.TweakShakeService
import com.charlie.androidtweaks.ui.TweakActivity
import com.charlie.androidtweaks.window.TweakLifeCyclerCallback
import java.lang.ref.WeakReference

object TweakManager {

    internal var isPersistent = true

    internal var isFloatWindow = false

    internal var library: TweakLibrary? = null

    internal var weakReference: WeakReference<Context>? = null

    private const val SP_FILE_NAME = "sp_tweak_file"

    internal lateinit var sharedPreferences: SharedPreferences

    private var isShakeEnable = false

    /**
     * App init
     */
    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
        sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
        return this
    }

    /**
     * must behind init
     */
    fun setLibrary(library: TweakLibrary): TweakManager {
        this.library = library
        return this
    }

    fun setPersistent(isPersistent: Boolean): TweakManager {
        this.isPersistent = isPersistent
        return this
    }

    fun setFloatWindow(isFloat: Boolean): TweakManager {
        isFloatWindow = isFloat
        if (isFloat) {
            //register lifecycle
            weakReference?.get()?.let {
                if (it is Application) {
                    it.registerActivityLifecycleCallbacks(TweakLifeCyclerCallback())
                }
            }
        }
        return this
    }

    /**
     * set shake enable
     */
    fun setShakeEnable(isEnable: Boolean): TweakManager {
        isShakeEnable = isEnable
        return this
    }

    /**
     * when the app destroy , isPersistent to save the key-value
     */
    fun destroy(context: Context) {
        if (!isPersistent) {
            reset()
        }
        if (isShakeEnable) {
            context.stopService(Intent(context, TweakShakeService::class.java))
        }
    }

    /**
     * init
     */
    fun init(tweakLibrary: TweakLibrary?) {
        library = tweakLibrary ?: return
        weakReference?.get()?.let {
            checkShakeConfig(it)
        }
    }

    /**
     * reset tweak value
     */
    fun reset() {
        library?.reset()
    }

    /**
     * start TweakActivity
     */
    fun start(tweakLibrary: TweakLibrary) {
        library = tweakLibrary
        weakReference?.get()?.let {
            if (library == null) {
                throw NullPointerException("tweak library can't be null")
            }
            val intent = Intent(it, TweakActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.startActivity(intent)
        }
    }

    /**
     * check isShake enable
     */
    private fun checkShakeConfig(context: Context) {
        if (isShakeEnable) {
            context.startService(Intent(context, TweakShakeService::class.java))
        }
    }

    /**
     * get tweaks
     */
    fun getTweaks() = library?.getTweaks()


}