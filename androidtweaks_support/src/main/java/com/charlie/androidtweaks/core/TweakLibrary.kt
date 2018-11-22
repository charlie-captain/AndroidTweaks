package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

abstract class TweakLibrary {

    abstract val tweakStore: TweakStore

    fun add(tweak: Tweak) {
        tweakStore.tweaks.add(tweak)
    }

    fun value(key: String): Any? {
        return tweakStore.valueOfTweak(key)
    }

    fun addAll(tweaks: ArrayList<Tweak>) {
        tweakStore.tweaks.addAll(tweaks)
    }

    fun reset() {
        tweakStore.reset()
    }

    fun getTweaks() = tweakStore.tweaks
}