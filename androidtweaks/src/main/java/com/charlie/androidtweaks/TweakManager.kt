package com.charlie.androidtweaks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.lang.ref.WeakReference

object TweakManager {

    private var tweaks: ArrayList<Tweak> = arrayListOf()

    open lateinit var weakReference: WeakReference<Context>

    val key = "key_tweak"

    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
        return this
    }

    fun add(t: Tweak): TweakManager {
        tweaks.add(t)
        return this
    }

    fun addAll(list: List<Tweak>): TweakManager {
        tweaks.addAll(list)
        return this
    }

    fun start() {
        weakReference.get().let {
            val intent = Intent(it, TweakActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable(key, tweaks)
            intent.putExtras(bundle)
            it?.startActivity(intent)
        }
    }

    fun clear() {
        weakReference.clear()
        tweaks.clear()
    }

}