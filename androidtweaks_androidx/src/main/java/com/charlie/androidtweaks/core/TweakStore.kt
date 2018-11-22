package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

class TweakStore {

    internal var tweaks: ArrayList<Tweak> = arrayListOf()

    fun valueOfTweak(key: String): Any? {
        for (tweak in tweaks) {
            if (tweak.toString() == key) {
                return tweak.value
            }
        }
        return null
    }

    fun reset() {
        var i = 0
        for (tweak in tweaks) {
            tweak.reset()
        }
    }

    fun listOf(vararg elements: Tweak): TweakStore {
        tweaks.addAll(elements)
        return this
    }
}