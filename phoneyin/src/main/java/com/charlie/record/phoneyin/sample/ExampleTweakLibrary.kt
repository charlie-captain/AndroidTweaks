package com.charlie.record.phoneyin.sample

import com.charlie.androidtweaks.core.TweakLibrary
import com.charlie.androidtweaks.core.TweakStore
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakViewDataType

object ExampleTweakLibrary : TweakLibrary() {

    //static
    val switchButton1 = Tweak("UI", "button", "visibility", TweakViewDataType.boolean, true)
    val switchButton2 = Tweak("Test", "category1", "button", TweakViewDataType.boolean, true)
    val switchButton3 = Tweak("Test", "category2", "button", TweakViewDataType.boolean, false)
    val switchButton4 = Tweak("Test", "category3", "button", TweakViewDataType.boolean, true)
    val switchButton5 = Tweak("Test", "category4", "button", TweakViewDataType.boolean, false)


    override val tweakStore = TweakStore().listOf(
        switchButton1,
        switchButton2,
        switchButton3,
        switchButton4,
        switchButton5
    )
}

