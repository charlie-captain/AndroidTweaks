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

    var defaultStringValue: String
    var stringValue: String

    init {
        defaultBoolValue = false
        maxIntValue = 0
        minIntValue = 0
        defaultIntValue = 0
        boolValue = defaultBoolValue
        intValue = defaultIntValue
        defaultStringValue = ""
        stringValue = ""
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

    constructor(
        topic: String,
        category: String,
        title: String,
        type: TweakViewDataType,
        defaultStringValue: String
    ) : this(topic, category, title, type) {
        this.defaultStringValue = defaultStringValue
    }

    //TweakDataViewType.integerEdit
    constructor(
        topic: String,
        category: String,
        title: String,
        type: TweakViewDataType,
        defaultIntValue: Int
    ) : this(topic, category, title, type) {
        this.defaultIntValue = defaultIntValue
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
        stringValue = defaultStringValue
    }
}