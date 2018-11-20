package com.charlie.androidtweaks

abstract class TweakLibrary {

    abstract val tweakStore: TweakStore

    fun bind(tweak: Tweak?, func: (a: Any) -> Unit) {
        tweakStore.bind(tweak, func)
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

    fun getTweakbyKey(key: String): Tweak? {
        return tweakStore.getTweakbyKey(key)
    }
}