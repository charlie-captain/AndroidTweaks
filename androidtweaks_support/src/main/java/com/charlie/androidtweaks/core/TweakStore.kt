package com.charlie.androidtweaks.core

import com.charlie.androidtweaks.data.EXCEPTION_ILLEGAL_ARGUMENT
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakViewDataType
import com.charlie.androidtweaks.utils.TweakSharePreferenceUtil

class TweakStore {

    internal var tweaks: ArrayList<Tweak> = arrayListOf()


    fun bind(tweak: Tweak?, func: (a: Any) -> Unit) {

        if (tweak == null) {
            return
        }

        if (TweakManager.weakReference?.get() == null) {
            return
        }
        func(currentValueforTweak(tweak))

    }

    fun unbind(tweak: Tweak) {

    }

    fun bindMultiple(tweaks: ArrayList<Tweak>, func: () -> Any?) {
        if (TweakManager.weakReference?.get() == null) {
            return
        }
        func()
    }

    fun currentValueforTweak(tweak: Tweak): Any {
        val key = createTweakKey(tweak)
        val value = when (tweak.type) {
            TweakViewDataType.boolean -> tweak.defaultBoolValue
            TweakViewDataType.integer -> tweak.defaultIntValue
            else -> {
                throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
            }
        }
        val spTweak by TweakSharePreferenceUtil(key, value)
        return spTweak
    }


    private fun createTweakKey(tweak: Tweak): String {
        return "${tweak.collection}_${tweak.category}_${tweak.title}"
    }

    fun getTweakFromKey(key: String): Tweak? {
        for (tweak in tweaks) {
            val keyTweak = createTweakKey(tweak)
            if (keyTweak == key) {
                return tweak
            }
        }
        return null
    }

    fun reset() {
        for (tweak in tweaks) {
            val key = createTweakKey(tweak)
            val value = when (tweak.type) {
                TweakViewDataType.boolean -> tweak.defaultBoolValue
                TweakViewDataType.integer -> tweak.defaultIntValue
                else -> {
                    throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                }
            }
            tweak.reset()
            var spTweak by TweakSharePreferenceUtil(key, value)
            spTweak = value
        }
    }

    fun listOf(vararg elements: Tweak): TweakStore {
        tweaks.addAll(elements)
        return this
    }
}