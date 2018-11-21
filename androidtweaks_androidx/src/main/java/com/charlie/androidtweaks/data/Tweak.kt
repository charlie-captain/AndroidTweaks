package com.charlie.androidtweaks.data

import java.io.Serializable

/**
 * why use Serializable?
 * :because parcelable is not supported
 */
data class Tweak(
    val collection: String,
    val category: String,
    val title: String,
    val type: TweakViewDataType
) : Serializable {

    var defaultBoolValue: Boolean

    var defaultIntValue: Int

    var maxIntValue: Int

    var minIntValue: Int

    var boolValue: Boolean

    var intValue: Int

    init {
        defaultBoolValue = false
        maxIntValue = 0
        minIntValue = 0
        defaultIntValue = 0
        boolValue = defaultBoolValue
        intValue = defaultIntValue
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


    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun reset() {
        boolValue = defaultBoolValue
        intValue = defaultIntValue
    }
}