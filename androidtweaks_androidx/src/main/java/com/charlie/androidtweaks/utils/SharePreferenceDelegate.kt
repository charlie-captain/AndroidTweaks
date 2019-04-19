package com.charlie.androidtweaks.utils

import android.util.Log
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS
import kotlin.reflect.KProperty

/**
 * Created by Charlie on 2019/04/19
 * @email: charlie.captain@foxmail.com
 */
class SharePreferenceDelegate<T>(private val key: String, private val default: T) {

    private val sharedPreferences = TweakManager.sharedPreferences

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        with(sharedPreferences) {
            try {
                val value = this.getString(key, null)
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
                return result as? T ?: default
            } catch (e: ClassCastException) {
                //remove the key
                this.edit().remove(key).apply()
                Log.d(TAG_ANDROIDTWEAKS, e.toString())
                return default
            }
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(sharedPreferences.edit()) {
            try {
                this.putString(key, value.toString())?.apply()
            } catch (e: Exception) {
                Log.d(TAG_ANDROIDTWEAKS, e.toString())
            }
        }

    }

}