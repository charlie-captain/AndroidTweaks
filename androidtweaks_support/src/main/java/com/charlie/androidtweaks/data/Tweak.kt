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


    var value: Any = type.getDefault()
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
                is TweakInt -> {
                    val spValue by TweakSharePreferenceUtil(toString(), type.value)
                    spValue
                }
                is TweakString->{
                    val spValue by TweakSharePreferenceUtil(toString(),type.value)
                    spValue
                }
                else -> {
                    throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                }
            }
        }

    /**
     * reset the value to default
     */
    fun reset() {
        value = when (type) {
            is TweakBool -> {
                type.defaultValue
            }
            is TweakInt -> {
                type.defaultValue
            }
            is TweakString->{
                type.defaultValue
            }
            else -> {
                throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
            }
        }
    }

    override fun toString(): String {

        return "${collection}_${category}_${title}"
    }
}