package com.charlie.record.phoneyin

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {
        btn.setOnClickListener {
            //            startActivity(Intent(this, TweakActivity::class.java))

//            var list: MutableList<Tweak<TweakType>> = arrayListOf()
//            val switchButton1 = Tweak<TBool>("AAA", "sa", "button", false)
//            val switchButton2 = Tweak("AAA", "sa", "seekbar", 14, 0, 100)
//            val switchButton3 = Tweak("AAA", "sa", "seekbar", 66, 0, 100)
//            val switchButton4 = Tweak("AAA", "sa", "seekbar", 11, 0, 100)
//            val switchButton5 = Tweak("AAA", "sa", "button", false)
//            val switchButton11 = Tweak("BBB", "sa", "button", false)
//            val switchButton12 = Tweak("BBB", "sb", "button", false)
//            val switchButton13 = Tweak("BBB", "sc", "button", false)
//            val switchButton14 = Tweak("BBB", "sd", "button", false)
//            list.add(switchButton1)
//            list.add(switchButton2)
//            list.add(switchButton3)
//            list.add(switchButton4)
//            list.add(switchButton5)
//            list.add(switchButton11)
//            list.add(switchButton12)
//            list.add(switchButton13)
//            list.add(switchButton14)
//            TweakManager.with(this).addAll(list).start()
        }
    }

    override fun setLayout() = R.layout.activity_main


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        var transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

}
