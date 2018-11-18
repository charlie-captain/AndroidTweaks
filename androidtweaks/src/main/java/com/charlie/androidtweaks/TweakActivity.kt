package com.charlie.androidtweaks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class TweakActivity : AppCompatActivity() {

    lateinit var baseFragment: TweakFragment
    lateinit var tweaks: ArrayList<Tweak<TweakType>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweak)
        tweaks = intent.getParcelableArrayListExtra<Tweak<TweakType>>(TweakManager.key)
        baseFragment = TweakFragment.newInstance(tweaks)
        for(i in tweaks){
            Log.d("ss","${i.maxValue}")
        }
        supportFragmentManager.inTransaction {
            replace(R.id.fl_tweak, baseFragment)
        }
    }

    fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TweakManager.clear()
    }
}
