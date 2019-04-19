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
data class Tweak<T>(
    val collection: String,
    val category: String,
    val title: String,
    val type: TweakType<T>,
    var callback: ((context: Context?, tweak: Tweak<T>) -> Unit)? = null
) {


    companion object;

    //fake delegate
    private val tweakValueDelegate: TweakValueDelegate<T> = TweakValueDelegate()

    var value: T
        internal set(value) = tweakValueDelegate.putTweakValue(toString(), value)
        get() = tweakValueDelegate.getTweakValue(toString(), type.getDefault())

    val isChanged: Boolean
        get() {
            if (!tweakValueDelegate.isInit) {
                value
            }
            return tweakValueDelegate.savedValue != null
        }

    /**
     * reset the value to default
     */
    fun reset() {
        tweakValueDelegate.reset()
    }

    /**
     * tweak key
     */
    override fun toString(): String {
        return "${collection}_${category}_$title"
    }

}
