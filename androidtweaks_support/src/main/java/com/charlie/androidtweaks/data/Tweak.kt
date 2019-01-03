package com.charlie.androidtweaks.data

import com.charlie.androidtweaks.utils.TweakSharePreferenceUtil
import java.io.Serializable

/**
 * why use Serializable?
 * :because parcelable is not supported
 */
data class Tweak(
    val collection: String,
    val category: String,
    val title: String,
    val type: TweakType
) : Serializable {


    internal var value: Any = type.getDefault()
        set(value) {
            var spValue by TweakSharePreferenceUtil(toString(), value)
            spValue = value
            field = spValue
        }
        get() {
            return when (type) {
                is TweakBool -> {
                    val spValue by TweakSharePreferenceUtil(toString(), type.value)
                    spValue
                }
                is TweakFloat -> {
                    val spValue by TweakSharePreferenceUtil(toString(), type.value)
                    spValue
                }
                is TweakString -> {
                    val spValue by TweakSharePreferenceUtil(toString(), type.value)
                    spValue
                }
                else -> {
                    throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                }
            }
        }

    var boolValue: Boolean = false
        get() {
            return value as? Boolean ?: field
        }

    var floatValue: Float = 0f
        get() {
            return value as? Float ?: field

        }

    var stringValue: String = String()
        get() {
            return value as? String ?: field
        }


    /**
     * reset the value to default
     */
    fun reset() {
        value = when (type) {
            is TweakBool -> {
                type.defaultValue
            }
            is TweakFloat -> {
                type.defaultValue
            }
            is TweakString -> {
                type.defaultValue
            }
            else -> {
                throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
            }
        }
    }

    override fun toString(): String {

        return "${collection}_${category}_$title"
    }
}