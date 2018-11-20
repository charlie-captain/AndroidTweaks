package com.charlie.record.phoneyin.sample

import com.charlie.androidtweaks.core.TweakLibrary
import com.charlie.androidtweaks.core.TweakStore
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakViewDataType

object ExampleTweakLibrary : TweakLibrary() {

    //static
    val switchButton1 =
        Tweak("UI", "button", "visibility", TweakViewDataType.boolean, true)
//    val switchButton2 = Tweak("UI", "button", "width", TweakViewDataType.integer, 14, 0, 100)
//    val switchButton3 = Tweak("UI", "button", "height", TweakViewDataType.integer, 66, 0, 100)
//    val switchButton4 = Tweak("UI", "button", "size", TweakViewDataType.integer, 11, 0, 100)
//    val switchButton5 = Tweak("UI", "button", "rotate", TweakViewDataType.integer, 0, 0, 100)
    val switchButton11 = Tweak("BBB", "sa", "button", TweakViewDataType.boolean, true)
    val switchButton12 = Tweak("BBB", "sb", "button", TweakViewDataType.boolean, false)
    val switchButton13 = Tweak("BBB", "sc", "button", TweakViewDataType.boolean, true)
    val switchButton14 = Tweak("BBB", "sd", "button", TweakViewDataType.boolean, false)


    override val tweakStore = TweakStore().apply {
        tweaks = arrayListOf(
            switchButton1,
//            switchButton2,
//            switchButton3,
//            switchButton4,
//            switchButton5,
            switchButton11,
            switchButton12,
            switchButton13,
            switchButton14
        )
    }

}

