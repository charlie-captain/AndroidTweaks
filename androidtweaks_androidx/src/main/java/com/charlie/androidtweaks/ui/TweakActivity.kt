package com.charlie.androidtweaks.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.Tweak
import kotlinx.android.synthetic.main.tweaks_toolbar.*

class TweakActivity : AppCompatActivity() {

    lateinit var baseFragmentFragment: TweakFragment
    lateinit var tweaks: ArrayList<Tweak>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweak)
        setSupportActionBar(tweaks_toolbar)
        tweaks_toolbar.title = "Android Tweaks"
        tweaks_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tweaks = intent.getSerializableExtra(TweakManager.key) as ArrayList<Tweak>
        baseFragmentFragment = TweakFragment.newInstance(tweaks)
        supportFragmentManager.inTransaction {
            replace(R.id.fl_tweak, baseFragmentFragment)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tweaks_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_toolbar_tweak_dissmiss -> finish()
            R.id.menu_toolbar_tweak_reset -> {
                TweakManager.reset()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        TweakManager.clear()
    }
}
