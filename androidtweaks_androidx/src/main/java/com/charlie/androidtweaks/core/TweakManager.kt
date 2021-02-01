package com.charlie.androidtweaks.core

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakMenuItem
import com.charlie.androidtweaks.shake.TweakShakeService
import com.charlie.androidtweaks.ui.TweakActivity
import com.charlie.androidtweaks.window.TweakLifeCyclerCallback
import java.lang.ref.WeakReference

object TweakManager {

    internal var isPersistent = true

    internal var isFloatWindow = false

    internal var library: TweakLibrary? = null

    internal var weakReference: WeakReference<Context>? = null

    private const val SP_FILE_NAME = "sp_tweak_file"

    internal lateinit var sharedPreferences: SharedPreferences

    private var isShakeEnable = false

    var onTweakMenuItemClickListener: OnTweakMenuItemClickListener? = null

    /**
     * App init
     */
    fun with(context: Context): TweakManager {
        weakReference = WeakReference(context)
        sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
        return this
    }

    /**
     * must behind init
     */
    fun setLibrary(library: TweakLibrary): TweakManager {
        this.library = library
        return this
    }

    fun setPersistent(isPersistent: Boolean): TweakManager {
        this.isPersistent = isPersistent
        return this
    }

    fun setFloatWindow(isFloat: Boolean): TweakManager {
        isFloatWindow = isFloat
        if (isFloat) {
            //register lifecycle
            weakReference?.get()?.let {
                if (it is Application) {
                    it.registerActivityLifecycleCallbacks(TweakLifeCyclerCallback())
                }
            }
        }
        return this
    }

    /**
     * set shake enable
     */
    fun setShakeEnable(isEnable: Boolean): TweakManager {
        isShakeEnable = isEnable
        return this
    }

    /**
     * when the app destroy , isPersistent to save the key-value
     */
    fun destroy(context: Context) {
        if (!isPersistent) {
            reset()
        }
        if (isShakeEnable) {
            context.stopService(Intent(context, TweakShakeService::class.java))
        }
    }

    /**
     * init
     */
    fun init() {
        requireNotNull(library) { "library must not be null!" }
        weakReference?.get()?.let {
            checkShakeConfig(it)
        }
    }

    /**
     * reset tweak value
     */
    fun reset() {
        library?.reset()
    }

    /**
     * custom tweak activity menu
     */
    fun setOnTweakMenuItemClickListener(onTweakMenuItemClickListener: OnTweakMenuItemClickListener): TweakManager {
        this.onTweakMenuItemClickListener = onTweakMenuItemClickListener
        return this
    }

    /**
     * start TweakActivity
     */
    fun startActivity(menuItems: ArrayList<TweakMenuItem>? = null) {
        val context = weakReference?.get() ?: return
        val tweakLibrary = library
        requireNotNull(tweakLibrary) { "tweak library can't be null" }

        if (tweakLibrary.tweakStore.isEmpty()) {
            //reflect class to tweak, add to list
            val list = ArrayList<Tweak<*>>()
            tweakLibrary.javaClass.declaredFields.forEach { field ->
                try {
                    if (field.type == Lazy::class.java) {
                        //init lazy value
                        field.isAccessible = true
                        val lazyTweak = (field.get(this) as? Lazy<*>)?.value as? Tweak<*>
                        if (lazyTweak != null) {
                            list.add(lazyTweak)
                        }
                    } else if (field.type == Tweak::class.java) {
                        field.isAccessible = true
                        val tweak = field.get(null) as Tweak<*>
                        list.add(tweak)
                    }
                } catch (e: Exception) {
                    Log.e(TAG_ANDROIDTWEAKS, "", e)
                }
            }
            tweakLibrary.addAll(list)
        }
        val intent = Intent(context, TweakActivity::class.java)
        menuItems?.let {
            intent.putExtras(Bundle().apply {
                this.putParcelableArrayList(TweakActivity.TWEAK_ARGS_MENU, it)
            })
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * check isShake enable
     */
    private fun checkShakeConfig(context: Context) {
        if (isShakeEnable) {
            context.startService(Intent(context, TweakShakeService::class.java))
        }
    }

    /**
     * get tweaks
     */
    fun getTweaks() = library?.getTweaks()


    interface OnTweakMenuItemClickListener {
        fun onTweakMenuItemClick(item: TweakMenuItem)
    }
}