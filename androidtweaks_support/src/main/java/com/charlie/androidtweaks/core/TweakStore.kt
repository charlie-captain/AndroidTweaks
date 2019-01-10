package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

class TweakStore {

    internal var tweaks: ArrayList<Tweak> = arrayListOf()

    internal fun valueOfTweak(key: String): Tweak? {
        for (tweak in tweaks) {
            if (tweak.toString() == key) {
                return tweak
            }
        }
        return null
    }

    internal fun reset() {
        for (tweak in tweaks) {
            tweak.reset()
        }
    }

    fun listOf(vararg elements: Tweak): TweakStore {
        tweaks.addAll(elements)
        return this
    }
}