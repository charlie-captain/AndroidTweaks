package com.charlie.androidtweaks

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlin.reflect.KProperty

class TweakSharepreference<T>(val name: String, val default: T) {

    private val spName = "sp_tweak_file"

    val sharedPreferences: SharedPreferences by lazy {
        TweakManager.weakReference.get().let {
            it?.getSharedPreferences(spName, Context.MODE_PRIVATE)!!
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getTweakValue(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putTweakValue(name, value)

    fun <T> putTweakValue(key: String, value: T) = with(sharedPreferences.edit()) {
        Log.d(TweakConstant.TAG_ANDROIDTWEAKS, "putValue $key   $value")
        when (value) {
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            else -> {
                throw IllegalArgumentException("SharePreference can't get this value.")
            }
        }

    }.apply()

    fun getTweakValue(key: String, default: T): T = with(sharedPreferences) {
        val value: Any = when (default) {
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            is String -> getString(key, default)

            else -> {
                throw IllegalArgumentException("SharePreference can't get this value.")
            }
        }
        Log.d(TweakConstant.TAG_ANDROIDTWEAKS, "getValue $key   $value")
        return value as T
    }
}