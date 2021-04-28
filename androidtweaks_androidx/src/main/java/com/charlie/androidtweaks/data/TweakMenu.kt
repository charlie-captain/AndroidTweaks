package com.charlie.androidtweaks.data

import android.content.Intent
import com.charlie.androidtweaks.data.TweakMenuItem
import com.charlie.androidtweaks.ui.TweakActivity
import java.util.*

/**
 * created by charlie on 2020/3/19
 */
abstract class TweakMenu : OnTweakMenuItemClickListener {

    private var tweakActivity: TweakActivity? = null

    fun attachActivity(activity: TweakActivity) {
        this.tweakActivity = activity
    }

    fun detachActivity() {
        this.tweakActivity = null
    }

    fun requireActivity(): TweakActivity {
        return tweakActivity ?: throw IllegalStateException("TweakMenu $this not attached to an activity.")
    }

    abstract fun getTweakMenuItem(): ArrayList<TweakMenuItem>?

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}

interface OnTweakMenuItemClickListener {
    fun onTweakMenuItemClick(item: TweakMenuItem)
}
