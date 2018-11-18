package com.charlie.androidtweaks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class TweakType {
    boolean
    ,
    integer
    ,
    float
    ,
    double
    ,
    color
    ,
    string
    ,
    stringList
    ,
    action
    //    boolean, integer, float, double, color, string, action
//    val defaultValue : TweakViewDataType

}


//
//class Bool : TweakType() {
//
//    init {
////        defaultValue = TweakViewDataType.boolean
//    }
//}
//
//class Int : TweakType() {
//
//}
//
//class TFloat : TweakType() {
//
//}

