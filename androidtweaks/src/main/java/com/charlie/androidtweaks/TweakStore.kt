package com.charlie.androidtweaks

class TweakStore {

    lateinit var tweaks: Set<Any>


    init {

    }

    fun bind(tweak: Tweak, func: (a:Any) -> Unit) {


        func(currentValueforTweak(tweak))

    }

    fun unbind(tweak: Tweak){

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
        return TweakSharepreference(key, value)
    }


    fun getTweakKey(tweak: Tweak): String {
        return "${tweak.collection}_${tweak.category}_${tweak.title}"
    }
}