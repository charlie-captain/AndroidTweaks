package com.charlie.androidtweaks.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_IS_KEY
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_KEY
import com.charlie.androidtweaks.data.TweakMenuItem
import com.charlie.androidtweaks.utils.SharePreferenceDelegate
import com.charlie.androidtweaks.utils.TWEAK_REQUESET_CODE_PERMIISION
import com.charlie.androidtweaks.utils.TweakPermissionUtil
import com.charlie.androidtweaks.window.TweakWindowManager
import com.charlie.androidtweaks.window.TweakWindowService
import kotlinx.android.synthetic.main.tweaks_toolbar.*

class TweakActivity : AppCompatActivity() {

    private var baseFragmentFragment: TweakFragment? = null

    private val TITLE_TOOLBAR = "Tweaks"

    private var floatWindowKey: String? = null

    //custom menu list
    private var menuList: ArrayList<TweakMenuItem>? = null

    companion object {

        const val TWEAK_ARGS_MENU = "tweak_args_menu"

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
        TweakManager.tweakMenu?.attachActivity(this)
        tweaks_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        floatWindowKey = intent.getStringExtra(SP_TWEAKS_FLOAT_WINDOW_KEY)
        var isFloat by SharePreferenceDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
        if (isFloat) {
            stopService(Intent(this, TweakWindowService::class.java))
        }
        baseFragmentFragment = TweakFragment()
        supportFragmentManager.inTransaction {
            replace(R.id.fl_tweak, baseFragmentFragment!!)
        }

        menuList = intent.getParcelableArrayListExtra(TWEAK_ARGS_MENU)

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
        if (menuList.isNullOrEmpty()) {
            menuInflater.inflate(R.menu.menu_tweaks_toolbar, menu)
        } else {
            menuList?.forEach { item ->
                menu?.add(item.groupId ?: Menu.NONE, item.itemId, Menu.NONE, item.title)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (TweakManager.isFloatWindow) {
            val isFloat by SharePreferenceDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
            menu?.findItem(R.id.menu_toolbar_tweak_close_window)?.isVisible = isFloat
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_toolbar_tweak_dissmiss -> {
                if (TweakManager.isFloatWindow) {
                    if (TweakPermissionUtil.checkPermission(this)) {
                        var putTweaks by SharePreferenceDelegate(
                            SP_TWEAKS_FLOAT_WINDOW_KEY,
                            baseFragmentFragment?.floatTweak
                        )
                        putTweaks = baseFragmentFragment?.floatTweak
                        var isFloat by SharePreferenceDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, true)
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
                    var isFloat by SharePreferenceDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
                    isFloat = false
                    TweakWindowManager.saveLayoutParams()
                    stopService(Intent(this, TweakWindowService::class.java))
                }
            }
            R.id.menu_toolbar_tweak_reset -> {
                TweakManager.reset()
                finish()
            }
            else -> {
                item?.let { menu ->
                    menuList?.indexOfFirst { menu.itemId == it.itemId }?.let { menuList?.get(it) }?.let {
                        TweakManager.tweakMenu?.onTweakMenuItemClick(it)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TWEAK_REQUESET_CODE_PERMIISION) {
            TweakPermissionUtil.onActivityResult(
                this,
                requestCode,
                resultCode,
                data,
                object : TweakPermissionUtil.OnPermissionListener {
                    override fun onPermissionGranted(isGranted: Boolean) {
                        if (isGranted) {
                            val putTweaks by SharePreferenceDelegate(
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
        } else {
            TweakManager.tweakMenu?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStop() {
        super.onStop()
        var isFloat by SharePreferenceDelegate(SP_TWEAKS_FLOAT_WINDOW_IS_KEY, false)
        if (isFloat) {
            startService(Intent(this, TweakWindowService::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TweakManager.tweakMenu?.detachActivity()
    }
}