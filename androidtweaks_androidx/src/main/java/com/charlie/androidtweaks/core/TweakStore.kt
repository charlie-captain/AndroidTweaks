package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

class TweakStore {

    internal var tweaks: ArrayList<Tweak<*>> = arrayListOf()

    fun <T> valueOfTweak(key: String): T? {
        for (tweak in tweaks) {
            if (tweak.toString() == key) {
                return tweak.value as? T ?: tweak.type.getDefault() as T?
            }
        }
        return null
    }

    fun reset() {
        for (tweak in tweaks) {
            tweak.reset()
        }
    }

    fun listOf(vararg elements: Tweak<*>): TweakStore {
        tweaks.addAll(elements)
        return this
    }
}