package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

abstract class TweakLibrary {

    abstract val tweakStore: TweakStore

    fun bind(tweak: Tweak?, func: (a: Any) -> Unit) {
        tweakStore.bind(tweak, func)
    }

    fun bindMultiple(tweaks: ArrayList<Tweak>, func: () -> Unit) {
        tweakStore.bindMultiple(tweaks, func)
    }

    fun unbind(tweak: Tweak) {
        tweakStore.unbind(tweak)
    }

    fun add(tweak: Tweak) {
        tweakStore.tweaks.add(tweak)
    }


    fun addAll(tweaks: ArrayList<Tweak>) {
        tweakStore.tweaks.addAll(tweaks)
    }

    fun getTweakFromKey(key: String): Tweak? {
        return tweakStore.getTweakFromKey(key)
    }

    fun getTweakValue(tweak: Tweak): Any? {
        return tweakStore.currentValueforTweak(tweak)
    }

    fun getTweakValue(key: String): Any? {
        return getTweakFromKey(key)?.let { tweakStore.currentValueforTweak(it) }
    }

    fun reset() {
        tweakStore.reset()
    }

    fun getTweaks() = tweakStore.tweaks
}