package com.charlie.androidtweaks.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

object TweakUtil {

    fun dp2px(dp: Float, resources: Resources): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }

    fun dimen(id: Int, resources: Resources): Int {
        return resources.getDimensionPixelSize(id)
    }


    fun screenSize(resources: Resources): DisplayMetrics {
        return resources.displayMetrics
    }

}