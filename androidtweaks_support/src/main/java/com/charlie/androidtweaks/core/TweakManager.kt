package com.charlie.androidtweaks.core

import android.app.Application
import android.content.Context
import com.charlie.androidtweaks.ui.TweakActivity
import com.charlie.androidtweaks.window.TweakLifeCyclerCallback
import java.lang.ref.WeakReference

object TweakManager {

    internal var library: TweakLibrary? = null

    var weakReference: WeakReference<Context>? = null


    internal var isPersistent = true

    internal var isFloatWindow = false

    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
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
     * when the app destroy , isPersistent to save the key-value
     */
    fun destroy() {
        if (!isPersistent) {
            reset()
        }
    }

    fun initLibrary(library: TweakLibrary): TweakManager {
        this.library = library
        return this
    }

    fun reset() {
        library?.reset()
    }


    fun start() {
        weakReference?.get()?.let {
            if (library == null) {
                throw NullPointerException("tweak library can't be null")
            }
            TweakActivity.start(it)
        }
    }

}