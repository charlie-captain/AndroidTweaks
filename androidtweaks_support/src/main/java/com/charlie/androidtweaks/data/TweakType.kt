package com.charlie.androidtweaks.data

import java.io.Serializable

abstract class TweakType : Serializable {
    abstract fun getValue(): Any
    abstract fun getDefault(): Any
}

/**
 * Boolean
 */
class TweakBool(val defaultValue: Boolean, var value: Boolean = defaultValue) : TweakType() {
    override fun getValue(): Any {
        return value
    }

    override fun getDefault(): Any {
        return defaultValue
    }
}

/**
 * Int
 */
class TweakInt(val defaultValue: Int, val min: Int, val max: Int, var value: Int = defaultValue) : TweakType() {
    override fun getValue(): Any {
        return value
    }

    override fun getDefault(): Any {
        return defaultValue
    }
}

/**
 * String
 */
class TweakString(val defaultValue: String, var value: String = defaultValue) : TweakType() {
    override fun getValue(): Any {
        return value
    }

    override fun getDefault(): Any {
        return defaultValue
    }

}