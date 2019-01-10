package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

abstract class TweakLibrary {

    abstract val tweakStore: TweakStore

    fun add(tweak: Tweak) {
        tweakStore.tweaks.add(tweak)
    }

    fun <T> value(key: String, default: T): T {
        val tweak = tweakStore.valueOfTweak(key)
        return if (tweak == null) default else tweak?.value as T
    }


    fun addAll(tweaks: ArrayList<Tweak>) {
        tweakStore.tweaks.addAll(tweaks)
    }

    internal fun reset() {
        tweakStore.reset()
    }

    fun getTweaks() = tweakStore.tweaks
}