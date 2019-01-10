package com.charlie.record.phoneyin.sample

import com.charlie.androidtweaks.core.TweakLibrary
import com.charlie.androidtweaks.core.TweakStore
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakBool
import com.charlie.androidtweaks.data.TweakFloat
import com.charlie.androidtweaks.data.TweakString

object ExampleTweakLibrary : TweakLibrary() {

    //static
    val switchButton1 = Tweak("UI", "button", "visibility", TweakBool(false))
    val switchButton2 = Tweak("Test", "category1", "button", TweakBool(false))
    val switchButton3 = Tweak("Test", "category2", "button", TweakBool(false))
    val switchButton4 = Tweak("Test", "category3", "button", TweakBool(false))
    val switchButton5 = Tweak("Test", "category4", "button", TweakBool(false))

    val stringtext = Tweak("UI", "EditText", "string", TweakString("default"))

    val inttext = Tweak("UI", "Int Text", "int", TweakFloat(0f, 1f))

    override val tweakStore = TweakStore().listOf(
        switchButton1,
        switchButton2,
        switchButton3,
        switchButton4,
        switchButton5,
        stringtext,
        inttext
    )
}

