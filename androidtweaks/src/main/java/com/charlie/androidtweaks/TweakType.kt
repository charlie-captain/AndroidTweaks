package com.charlie.androidtweaks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class TweakType : Parcelable {
//    boolean
//    ,
//    integer
//    ,
//    float
//    ,
//    double
//    ,
//    color
//    ,
//    string
//    ,
//    stringList
//    ,
//    action
    //    boolean, integer, float, double, color, string, action
//    val defaultValue : TweakViewDataType

    open var tweakViewDataType: TweakViewDataType = TweakViewDataType.default

}

enum class TweakViewDataType {
    boolean,
    integer,
    cgFloat,
    double,
    uiColor,
    string,
    stringList,
    action,
    default
}

class TBool : TweakType() {
    override var tweakViewDataType: TweakViewDataType = TweakViewDataType.boolean
}

class TInt : TweakType() {
    override var tweakViewDataType: TweakViewDataType = TweakViewDataType.integer
}

class TFloat : TweakType() {

    override var tweakViewDataType: TweakViewDataType = TweakViewDataType.cgFloat
}



