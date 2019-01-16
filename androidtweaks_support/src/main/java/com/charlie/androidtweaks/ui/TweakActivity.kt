package com.charlie.androidtweaks.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_IS_KEY
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_KEY
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.utils.TweakPermissionUtil
import com.charlie.androidtweaks.utils.TweakValueDelegate
import com.charlie.androidtweaks.window.TweakWindowManager
import com.charlie.androidtweaks.window.TweakWindowService
import kotlinx.android.synthetic.main.tweaks_toolbar.*

private const val TITLE_TOOLBAR = "Tweaks"

class TweakActivity : AppCompatActivity() {
    private var baseFragmentFragment: TweakFragment? = null
    private var tweaks: ArrayList<Tweak>? = null
    private var floatWindowKey: String? = null

    companion object {

        fun start(context: Context, tweakKey: String = "") {
            val intent = Intent(context, TweakActivity::class.java)
            intent.putExtra(SP_TWEAKS_FLOAT_WINDOW_KEY, tweakKey)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweak)
        tweaks_toolbar.title = TITLE_TOOLBAR
        setSupportActionBar(tweaks_toolbar)
        tweaks_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tweaks = TweakManager.getTweaks() ?: arrayListOf()
        Log.d(TAG_ANDROIDTWEAKS, tweaks.toString())
        floatWindowKey = intent.getStringExtra(SP_TWEAKS_FLOAT_WINDOW_KEY)
        var isFloat by TweakValueDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
        if (isFloat) {
            stopService(Intent(this, TweakWindowService::class.java))
        }
        if (tweaks == null) {
            tweaks = arrayListOf()
        }
        baseFragmentFragment = TweakFragment.newInstance(tweaks!!)
        supportFragmentManager.inTransaction {
            replace(R.id.fl_tweak, baseFragmentFragment!!)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            tweaks_toolbar.title = TITLE_TOOLBAR
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tweaks_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (TweakManager.isFloatWindow) {
            val isFloat by TweakValueDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
            menu?.findItem(R.id.menu_toolbar_tweak_close_window)?.isVisible = isFloat
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_toolbar_tweak_dissmiss -> {
                if (TweakManager.isFloatWindow) {
                    if (TweakPermissionUtil.checkPermission(this)) {
                        var putTweaks by TweakValueDelegate(
                            SP_TWEAKS_FLOAT_WINDOW_KEY,
                            baseFragmentFragment?.floatTweak
                        )
                        putTweaks = baseFragmentFragment?.floatTweak
                        var isFloat by TweakValueDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, true)
                        isFloat = true
                        startService(Intent(this, TweakWindowService::class.java))
                    } else {
                        TweakPermissionUtil.applyPermission(this)
                    }
                }
                finish()
            }
            R.id.menu_toolbar_tweak_close_window -> {
                if (TweakManager.isFloatWindow) {
                    var isFloat by TweakValueDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
                    isFloat = false
                    TweakWindowManager.saveLayoutParams()
                    stopService(Intent(this, TweakWindowService::class.java))
                }
            }
            R.id.menu_toolbar_tweak_reset -> {
                TweakManager.reset()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        TweakPermissionUtil.onActivityResult(
            this,
            requestCode,
            resultCode,
            data,
            object : TweakPermissionUtil.OnPermissionListener {
                override fun onPermissionGranted(isGranted: Boolean) {
                    if (isGranted) {
                        val putTweaks by TweakValueDelegate(
                            SP_TWEAKS_FLOAT_WINDOW_KEY,
                            baseFragmentFragment?.floatTweak
                        )
                        startService(Intent(this@TweakActivity, TweakWindowService::class.java))
                    } else {
                        Toast.makeText(
                            this@TweakActivity,
                            getString(R.string.string_toast_float_window_permission),
                            Toast.LENGTH_LONG
                        )
                    }
                }
            })
    }

    override fun onStop() {
        super.onStop()
        var isFloat by TweakValueDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
        if (isFloat) {
            startService(Intent(this, TweakWindowService::class.java))
        }
    }
}
