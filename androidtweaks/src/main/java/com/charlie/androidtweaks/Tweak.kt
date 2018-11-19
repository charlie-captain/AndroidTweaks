package com.charlie.androidtweaks

import java.io.Serializable

data class Tweak(
    val collection: String,
    val category: String,
    val title: String,
    val type :TweakViewDataType
) : Serializable {

    var defaultBoolValue: Boolean

    var defaultIntValue: Int

    var maxIntValue: Int

    var minIntValue: Int

    init {
        defaultBoolValue = false
        maxIntValue = 0
        minIntValue = 0
        defaultIntValue = 0
    }
    constructor(
        topic: String, category: String, title: String, type: TweakViewDataType, defaultValue: Boolean
    ) : this(topic, category, title, type) {
        defaultBoolValue = defaultValue
    }
    constructor(
        topic: String, category: String, title: String, type: TweakViewDataType, defaultValue: Int,
        minValue: Int,
        maxValue: Int
    ) : this(topic, category, title, type) {
        defaultIntValue = defaultValue
        maxIntValue = maxValue
        minIntValue = minValue
    }


}