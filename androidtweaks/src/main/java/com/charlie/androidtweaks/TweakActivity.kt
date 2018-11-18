package com.charlie.androidtweaks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class TweakActivity : AppCompatActivity() {

    var baseFragment: TweakFragment

    init {
        var list: MutableList<Tweak> = arrayListOf()
        val switchButton1 = Tweak("AAA", "sa", "button", TweakType.boolean, false)
        val switchButton2 = Tweak("AAA", "sa", "seekbar", TweakType.integer, 14, 0, 100)
        val switchButton3 = Tweak("AAA", "sa", "seekbar", TweakType.integer, 66, 0, 100)
        val switchButton4 = Tweak("AAA", "sa", "seekbar", TweakType.integer, 11, 0, 100)
        val switchButton5 = Tweak("AAA", "sa", "button", TweakType.boolean, false)
        val switchButton11 = Tweak("BBB", "sa", "button", TweakType.boolean, false)
        val switchButton12 = Tweak("BBB", "sb", "button", TweakType.boolean, false)
        val switchButton13 = Tweak("BBB", "sc", "button", TweakType.boolean, false)
        val switchButton14 = Tweak("BBB", "sd", "button", TweakType.boolean, false)
        list.add(switchButton1)
        list.add(switchButton2)
        list.add(switchButton3)
        list.add(switchButton4)
        list.add(switchButton5)
        list.add(switchButton11)
        list.add(switchButton12)
        list.add(switchButton13)
        list.add(switchButton14)
        baseFragment = TweakFragment.newInstance(list as ArrayList<Tweak>)
    }

    fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweak)

        supportFragmentManager.inTransaction {
            add(R.id.fl_tweak, baseFragment).show(baseFragment)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            supportFragmentManager.inTransaction {
                //                show(baseFragment)
            }
        } else {
            super.onBackPressed()
        }
    }
}
