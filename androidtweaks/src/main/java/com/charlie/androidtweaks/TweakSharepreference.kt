package com.charlie.androidtweaks

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

class TweakSharepreference<T>(val name: String, val default: T) {

    private val spName = "sp_tweak_file"

    val sharedPreferences: SharedPreferences by lazy {
        TweakManager.weakReference.get()!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getTweakValue(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putTweakValue(name, value)

    fun <T> putTweakValue(key: String, value: T) = with(sharedPreferences.edit()) {

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

    fun getTweakValue(name: String, default: T): T = with(sharedPreferences) {
        val value: Any = when (default) {
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is String -> getString(name, default)

            else -> {
                throw IllegalArgumentException("SharePreference can't get this value.")
            }
        }
        return value as T
    }
}