package com.charlie.androidtweaks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Tweak<T : TweakType>(
    val collection: String,
    val category: String,
    val title: String,
    val defaultValue: T? = null,
    val maxValue: T? = null,
    val minValue: T? = null

) : Parcelable {

//    var defaultBoolValue: Boolean
//
//    var defaultIntValue: Int
//
//    var maxIntValue: Int
//
//    var minIntValue: Int
//
//    init {
//        defaultBoolValue = false
//        maxIntValue = 0
//        minIntValue = 0
//        defaultIntValue = 0
//    }
//
//    constructor(
//        topic: String, category: String, title: String, type: TweakType, defaultValue: Int,
//        minValue: Int,
//        maxValue: Int
//    ) : this(topic, category, title, type) {
//        defaultIntValue = defaultValue
//        maxIntValue = maxValue
//        minIntValue = minValue
//    }
//
//    constructor(
//        topic: String, category: String, title: String, type: TweakType, defaultValue: Float,
//        maxValue: Float,
//        minValue: Float
//    ) : this(topic, category, title, type)
//
//    constructor(
//        topic: String, category: String, title: String, type: TweakType, defaultValue: Double,
//        maxValue: Double,
//        minValue: Double
//    ) : this(topic, category, title, type)
//
//    constructor(
//        topic: String, category: String, title: String, type: TweakType, defaultValue: Boolean
//    ) : this(topic, category, title, type) {
//        defaultBoolValue = defaultValue
//    }


}