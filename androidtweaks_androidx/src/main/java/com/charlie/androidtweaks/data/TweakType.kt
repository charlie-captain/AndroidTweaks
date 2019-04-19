package com.charlie.androidtweaks.data

abstract class TweakType<out T> {
    abstract fun getDefault(): T
}

/**
 * Boolean
 * @param isGroup: 是否群组，如果为false，同category下的preference enable为false
 */
class TweakBool(val defaultValue: Boolean, val isGroup: Boolean = false) : TweakType<Boolean>() {

    override fun getDefault(): Boolean {
        return defaultValue
    }
}

/**
 * Float
 * @param increment: 增量 float,默认为1f
 * @param isNegative: 是否负数
 */
class TweakFloat(
    val defaultValue: Float,
    val increment: Float = 1f,
    val min: Float = 0f,
    val max: Float = 100f,
    val isNegative: Boolean = false
) : TweakType<Float>() {

    override fun getDefault(): Float {
        return defaultValue
    }

}

/**
 * String
 */
class TweakString(val defaultValue: String) : TweakType<String>() {

    override fun getDefault(): String {
        return defaultValue
    }

}

/**
 * Double
 */
class TweakDouble(
    val defaultValue: Double,
    val increment: Double = 1.0,
    val min: Double = 0.0,
    val max: Double = 100.0,
    val isNegative: Boolean = false
) : TweakType<Double>() {

    override fun getDefault(): Double {
        return defaultValue
    }

}