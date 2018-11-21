package com.charlie.androidtweaks.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.charlie.androidtweaks.ui.TweakActivity
import java.lang.ref.WeakReference

object TweakManager {

    private lateinit var library: TweakLibrary

    var weakReference: WeakReference<Context>? = null

    val key = "key_tweak"

    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
        return this
    }


    fun initLibrary(library: TweakLibrary) :TweakManager {
        this.library = library
        return this
    }

    fun reset() {
        library.reset()
    }

    /**
     * when application was killed that the ps.clear
     */
    fun applyPersistent(isPersistent: Boolean) {

    }

    fun start() {
        weakReference?.get().let {
            val intent = Intent(it, TweakActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable(
                key,
                library.getTweaks()
            )
            intent.putExtras(bundle)
            it?.startActivity(intent)
        }
    }

    fun clear() {
//        tweaks.clear()
    }

}