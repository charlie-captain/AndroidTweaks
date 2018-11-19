package com.charlie.record.phoneyin

import com.charlie.androidtweaks.Tweak
import com.charlie.androidtweaks.TweakLibrary
import com.charlie.androidtweaks.TweakViewDataType

object ExampleTweakLibrary : TweakLibrary() {


    val switchButton1 = Tweak("AAA", "sa", "button", TweakViewDataType.boolean, true)
    val switchButton2 = Tweak("AAA", "sa", "seekbar", TweakViewDataType.integer,14, 0, 100)
    val switchButton3 = Tweak("AAA", "sa", "seekbar", TweakViewDataType.integer,66, 0, 100)
    val switchButton4 = Tweak("AAA", "sa", "seekbar", TweakViewDataType.integer,11, 0, 100)
    val switchButton5 = Tweak("AAA", "sa", "button", TweakViewDataType.boolean,false)
    val switchButton11 = Tweak("BBB", "sa", "button", TweakViewDataType.boolean, true)
    val switchButton12 = Tweak("BBB", "sb", "button", TweakViewDataType.boolean, false)
    val switchButton13 = Tweak("BBB", "sc", "button", TweakViewDataType.boolean, true)
    val switchButton14 = Tweak("BBB", "sd", "button", TweakViewDataType.boolean, false)

}