package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak

class TweakStore {

    private val tweaks: ArrayList<Tweak<*>> = arrayListOf()

    fun isEmpty(): Boolean {
        return tweaks.isEmpty()
    }

    fun addAll(list: List<Tweak<*>>) {
        tweaks.addAll(list)
    }

    fun getTweaks() = tweaks.toList()

    fun reset() {
        for (tweak in tweaks) {
            tweak.reset()
        }
    }
}