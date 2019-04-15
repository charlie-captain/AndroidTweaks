package com.charlie.androidtweaks.data

import android.content.Context
import com.charlie.androidtweaks.utils.TweakValueDelegate

/**
 * Tweak
 * @param collection 一级目录
 * @param category 二级分类
 * @param title item的命名
 * @param type 参考TweakType
 * @param callback 回调接口，在数据发生变化时
 */
data class Tweak(
    val collection: String,
    val category: String,
    val title: String,
    val type: TweakType,
    var callback: ((context: Context?, tweak: Tweak) -> Unit)? = null
) {

    companion object;

    internal var value: Any by TweakValueDelegate(this, type.getValue())

    var isChanged: Boolean = false
        internal set

    var boolValue: Boolean = false
        get() {
            return value as? Boolean ?: field
        }
        private set

    var floatValue: Float = 0f
        get() {
            return value as? Float ?: field

        }
        private set

    var stringValue: String = String()
        get() {
            return value as? String ?: field
        }
        private set

    var doubleValue: Double = 0.0
        get() {
            return value as? Double ?: field
        }
        private set


    /**
     * reset the value to default
     */
    fun reset() {
        value = type.getDefault()
    }

    /**
     * tweak key
     */
    override fun toString(): String {
        return "${collection}_${category}_$title"
    }

}
