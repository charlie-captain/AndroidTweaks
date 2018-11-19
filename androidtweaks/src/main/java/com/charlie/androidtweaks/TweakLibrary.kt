package com.charlie.androidtweaks

open class TweakLibrary {

    companion object {

        open var tweakStore = TweakStore()
    }

    open fun bind(tweak: Tweak, func: (a: Any) -> Unit) {
        tweakStore.bind(tweak, func)
    }

    open fun unbind(tweak: Tweak) {
            }


}