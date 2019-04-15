package com.charlie.androidtweaks.utils

import android.util.Log
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS
import com.charlie.androidtweaks.data.Tweak
import kotlin.reflect.KProperty

class TweakValueDelegate<T>(val tweak: Tweak, val default: T) {

    private val sharedPreferences = TweakManager.sharedPreferences

    private var mSaveValue: Any? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getTweakValue(tweak.toString(), default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putTweakValue(tweak.toString(), value)

    private fun <T> putTweakValue(key: String, value: T) = with(sharedPreferences.edit()) {
        //        Log.d(TAG_ANDROIDTWEAKS, "putValue $key   $value")
        try {
            this.putString(key, value.toString())?.apply()
            mSaveValue = value
        } catch (e: Exception) {
            Log.d(TAG_ANDROIDTWEAKS, e.toString())
        }
    }

    private fun getTweakValue(key: String, default: T): T = with(sharedPreferences) {
        if (mSaveValue != null) {
//            Log.d(TAG_ANDROIDTWEAKS, "restore Key=$key value = $mSaveValue")
            return mSaveValue as T
        }
        try {
            val value = this.getString(key, null)
            if (value == null) {
//                Log.d(TAG_ANDROIDTWEAKS, "getDefault $key   $default")
                mSaveValue = default
                return default
            } else {
                val result = when (default) {
                    is Int -> value.toInt()
                    is Boolean -> value.toBoolean()
                    is Float -> value.toFloat()
                    is String -> value
                    is Double -> value.toDouble()
                    else -> {
                        throw IllegalArgumentException("SharePreference can't get this value.")
                    }
                }
                tweak.isChanged = true
                mSaveValue = result
//                Log.d(TAG_ANDROIDTWEAKS, "getValue $key   $result")
                return result as T
            }
        } catch (e: ClassCastException) {
            //remove the key
            this.edit().remove(key).apply()
            Log.d(TAG_ANDROIDTWEAKS, e.toString())
            return default
        }
    }
}