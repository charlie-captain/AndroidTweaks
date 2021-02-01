package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

abstract class TweakLibrary {

    val tweakStore: TweakStore = TweakStore()

    internal fun addAll(tweaks: ArrayList<Tweak<*>>) {
        tweakStore.addAll(tweaks)
    }

    internal fun reset() {
        tweakStore.reset()
    }

    internal fun getTweaks() = tweakStore.getTweaks()
}