package com.charlie.androidtweaks

import android.content.Context
import android.content.Intent
import android.util.Log
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

object TweakManager {

    private var tweaks: ArrayList<Tweak<TweakType>> = arrayListOf()

    private lateinit var weakReference: WeakReference<Context>

    val key = "key_tweak"

    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
        return this
    }

    fun add(t: Tweak<TweakType>): TweakManager {
        tweaks.add(t)
        return this
    }

    fun addAll(list: List<Tweak<TweakType>>): TweakManager {
        tweaks.addAll(list)
        return this
    }

    fun start() {
        weakReference.get().let {
            val intent = Intent(it, TweakActivity::class.java)
            intent.putParcelableArrayListExtra(key, tweaks)
            for (i in tweaks) {
                Log.d("dd", "${i.maxValue}")
            }
            it?.startActivity(intent)
        }
    }

    fun clear() {
        weakReference.clear()
        tweaks.clear()
    }

}