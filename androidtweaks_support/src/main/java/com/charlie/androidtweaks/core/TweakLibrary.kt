package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

abstract class TweakLibrary {

    abstract val tweakStore: TweakStore

    fun add(tweak: Tweak<*>) {
        tweakStore.tweaks.add(tweak)
    }

    /**
     * use for dynamic tweak
     */
    fun <T> value(key: String, default: T): T {
        val value: T? = tweakStore.valueOfTweak(key)
        return value ?: default
    }


    fun addAll(tweaks: ArrayList<Tweak<*>>) {
        tweakStore.tweaks.addAll(tweaks)
    }

    fun reset() {
        tweakStore.reset()
    }

    fun getTweaks() = tweakStore.tweaks
}