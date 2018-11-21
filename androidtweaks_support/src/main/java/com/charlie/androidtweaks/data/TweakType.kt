package com.charlie.androidtweaks.data

import java.io.Serializable

//@Parcelize
//open class TweakType : Parcelable {
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
//    //    boolean, integer, float, double, color, string, action
////    val defaultValue : TweakViewDataType
//
//    open var tweakViewDataType: TweakViewDataType = TweakViewDataType.default
//
//}

enum class TweakViewDataType : Serializable {
    boolean,
    integer,
    string,
    intergerEdit
}


