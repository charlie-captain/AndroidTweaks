package com.charlie.androidtweaks.data

abstract class TweakType {
    abstract fun getValue(): Any
    abstract fun getDefault(): Any
}

/**
 * Boolean
 * @param isGroup: 是否群组，如果为false，同category下的preference enable为false
 */
class TweakBool(val defaultValue: Boolean, val isGroup: Boolean = false) : TweakType() {
    private var value: Boolean = defaultValue

    override fun getValue(): Any {
        return value
    }

    override fun getDefault(): Any {
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
) : TweakType() {
    private var value: Float = defaultValue

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
class TweakString(val defaultValue: String) : TweakType() {
    private var value: String = defaultValue
    override fun getValue(): Any {
        return value
    }

    override fun getDefault(): Any {
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
) : TweakType() {
    private var value: Double = defaultValue
    override fun getValue(): Any {
        return value
    }

    override fun getDefault(): Any {
        return defaultValue
    }

}