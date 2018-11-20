package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakViewDataType
import com.charlie.androidtweaks.utils.TweakSharePreference

class TweakStore {

    lateinit var tweaks: ArrayList<Tweak>


    init {

    }

    fun bind(tweak: Tweak?, func: (a: Any) -> Unit) {

        if (tweak == null) {
            return
        }

        func(currentValueforTweak(tweak))

    }

    fun unbind(tweak: Tweak) {

    }

    fun currentValueforTweak(tweak: Tweak): Any {
        val key = getTweakKey(tweak)
        val value = when (tweak.type) {
            TweakViewDataType.boolean -> tweak.defaultBoolValue
            TweakViewDataType.integer -> tweak.defaultIntValue
            else -> {
                throw IllegalArgumentException("androidTweaks unable to deal with this type.")
            }
        }
        val spTweak by TweakSharePreference(key, value)

        return spTweak
    }


    fun getTweakKey(tweak: Tweak): String {
        return "${tweak.collection}_${tweak.category}_${tweak.title}"
    }

    fun getTweakbyKey(key: String): Tweak? {
        for (tweak in tweaks) {
            val keyTweak = getTweakKey(tweak)
            if (keyTweak == key) {
                return tweak
            }
        }
        return null
    }
}