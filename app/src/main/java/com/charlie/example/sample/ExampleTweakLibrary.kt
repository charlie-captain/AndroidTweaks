package com.charlie.example.sample

import com.charlie.androidtweaks.core.TweakLibrary
import com.charlie.androidtweaks.core.TweakStore
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakBool
import com.charlie.androidtweaks.data.TweakFloat
import com.charlie.androidtweaks.data.TweakString

object ExampleTweakLibrary : TweakLibrary() {

    //static
    val switchButton1 by lazy {
        Tweak("UI", "button", "visibility", TweakBool(false))
    }
    val switchButton2 by lazy {
        Tweak("Test", "category1", "button", TweakBool(false))
    }
    val switchButton3 by lazy { Tweak("Test", "category2", "button", TweakBool(false)) }
    val switchButton4 by lazy { Tweak("Test", "category3", "button", TweakBool(false)) }
    val switchButton5 by lazy { Tweak("Test", "category4", "button", TweakBool(false)) }

    val stringtext by lazy { Tweak("UI", "EditText", "string", TweakString("default")) }

    val inttext by lazy { Tweak("UI", "Int Text", "int", TweakFloat(0f, 1f)) }

//    override val tweakStore = TweakStore().listOf(
//        switchButton1,
//        switchButton2,
//        switchButton3,
//        switchButton4,
//        switchButton5,
//        stringtext,
//        inttext
//    )
}

