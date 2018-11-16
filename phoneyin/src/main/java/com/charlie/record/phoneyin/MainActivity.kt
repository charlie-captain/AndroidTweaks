package com.charlie.record.phoneyin

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.charlie.androidtweaks.TweakFragment

class MainActivity : BaseActivity() {

    override fun init() {

//        supportFragmentManager.inTransaction {
//            replace(R.id.fl_content, TweakFragment())  }

        supportFragmentManager.beginTransaction().replace(R.id.fl_content,Fff())
            .commit()
    }

    override fun setLayout() = R.layout.activity_main


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.()->Unit){
        var transaction= beginTransaction()
        transaction.func()
        transaction.commit()
    }

}
