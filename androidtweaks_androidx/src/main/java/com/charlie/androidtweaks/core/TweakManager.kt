package com.charlie.androidtweaks.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.charlie.androidtweaks.ui.TweakActivity
import java.lang.ref.WeakReference

object TweakManager {

    private var library: TweakLibrary? = null

    var weakReference: WeakReference<Context>? = null

    const val key = "key_tweak"

    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
        return this
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
            val intent = Intent(it, TweakActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            var bundle = Bundle()
            bundle.putSerializable(
                key,
                library?.getTweaks()
            )
            intent.putExtras(bundle)
            it.startActivity(intent)
        }
    }

}