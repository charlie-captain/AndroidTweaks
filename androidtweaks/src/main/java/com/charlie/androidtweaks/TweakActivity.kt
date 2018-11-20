package com.charlie.androidtweaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.include_toolbar.*

class TweakActivity : AppCompatActivity() {

    lateinit var baseFragment: TweakFragment
    lateinit var tweaks: ArrayList<Tweak>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweak)
        setSupportActionBar(toolbar)
        toolbar.title = "Android Tweaks"
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tweaks = intent.getSerializableExtra(TweakManager.key) as ArrayList<Tweak>
        baseFragment = TweakFragment.newInstance(tweaks)
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
    }
}
