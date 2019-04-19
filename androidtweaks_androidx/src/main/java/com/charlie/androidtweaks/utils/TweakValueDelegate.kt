package com.charlie.androidtweaks.utils

import android.util.Log
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS

class TweakValueDelegate<T> {

    private val sharedPreferences = TweakManager.sharedPreferences

    internal var savedValue: Any? = null

    internal var isInit = false

    internal fun putTweakValue(key: String, value: T) {
        if (value == null) {
            reset()
            return
        }
        with(sharedPreferences.edit()) {
            //            Log.d(TAG_ANDROIDTWEAKS, "putValue $key   $value")
            try {
                this.putString(key, value.toString())?.apply()
                savedValue = value
            } catch (e: Exception) {
                Log.d(TAG_ANDROIDTWEAKS, e.toString())
            }
        }
    }

    internal fun getTweakValue(key: String, default: T): T {
        if (savedValue != null) {
//            Log.d(TAG_ANDROIDTWEAKS, "restore Key=$key value = $savedValue")
            return savedValue as T
        } else if (isInit) {
            //init once
//            Log.d(TAG_ANDROIDTWEAKS, "isInitNull Key=$key value = $default")
            return default
        }
        with(sharedPreferences) {
            try {
                val value = this.getString(key, null)
                if (value == null) {
//                    Log.d(TAG_ANDROIDTWEAKS, "getNull $key   $default")
                    savedValue = null
                } else {
                    val result = when (default) {
                        is Int -> value.toIntOrNull()
                        is Boolean -> value.toBoolean()
                        is Float -> value.toFloatOrNull()
                        is String -> value
                        is Double -> value.toDoubleOrNull()
                        else -> {
                            throw IllegalArgumentException("SharePreference can't get this value.")
                        }
                    }
                    savedValue = result
//                    Log.d(TAG_ANDROIDTWEAKS, "getValue $key   $result")
                }
                isInit = true
                return savedValue as? T ?: default
            } catch (e: ClassCastException) {
                reset()
                //remove the key
                this.edit().remove(key).apply()
                Log.d(TAG_ANDROIDTWEAKS, e.toString())
                return default
            }
        }
    }

    fun reset() {
        savedValue = null
        isInit = false
    }
}