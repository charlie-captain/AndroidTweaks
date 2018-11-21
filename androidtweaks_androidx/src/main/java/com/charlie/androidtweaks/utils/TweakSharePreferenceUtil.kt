package com.charlie.androidtweaks.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS
import kotlin.reflect.KProperty

class TweakSharePreferenceUtil<T>(val name: String, val default: T) {

    private val spName = "sp_tweak_file"

    val sharedPreferences: SharedPreferences? by lazy {
        TweakManager.weakReference.get().let {
            it?.getSharedPreferences(spName, Context.MODE_PRIVATE)
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getTweakValue(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putTweakValue(name, value)

    fun <T> putTweakValue(key: String, value: T) = with(sharedPreferences?.edit()) {
        Log.d(TAG_ANDROIDTWEAKS, "putValue $key   $value")
        when (value) {
            is Int -> this?.putInt(key, value)
            is Boolean -> this?.putBoolean(key, value)
            is Float -> this?.putFloat(key, value)
            is String -> this?.putString(key, value)
            else -> {
                throw IllegalArgumentException("SharePreference can't get this value.")
            }
        }

    }?.apply()

    fun getTweakValue(key: String, default: T): T = with(sharedPreferences) {
        val value: Any = when (default) {
            is Int -> this?.getInt(key, default) as Any
            is Boolean -> this?.getBoolean(key, default) as Any
            is Float -> this?.getFloat(key, default) as Any
            is String -> this?.getString(key, default) as Any

            else -> {
                throw IllegalArgumentException("SharePreference can't get this value.")
            }
        }
        Log.d(TAG_ANDROIDTWEAKS, "getValue $key   $value")
        return value as T
    }
}