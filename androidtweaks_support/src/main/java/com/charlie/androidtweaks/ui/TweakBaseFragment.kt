package com.charlie.androidtweaks.ui

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.preference.*
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * fix left padding bug
 */
abstract class TweakBaseFragment : PreferenceFragmentCompat() {

    //fix the left padding
//    override fun setPreferenceScreen(preferenceScreen: PreferenceScreen?) {
//        super.setPreferenceScreen(preferenceScreen)
//        if (preferenceScreen != null) {
//            val count = preferenceScreen.preferenceCount
//            for (i in 0 until count) {
//                if (preferenceScreen.getPreference(i) is PreferenceCategory) {
//                    val category = preferenceScreen.getPreference(i) as PreferenceCategory
//                    val childCount = category.preferenceCount
//                    for (j in 0 until childCount) {
//                        category.getPreference(j).isIconSpaceReserved = false
//                    }
//                }
//                preferenceScreen.getPreference(i).isIconSpaceReserved = false
//            }
//        }
//    }

    //暂时解决，需要等待系统修复bug，比如AndroidX 已经修复就可以使用上面方法消除左边空间
    override fun onCreateAdapter(preferenceScreen: PreferenceScreen?): RecyclerView.Adapter<*> {
        return object : PreferenceGroupAdapter(preferenceScreen) {
            @SuppressLint("RestrictedApi")
            override fun onBindViewHolder(holder: PreferenceViewHolder, position: Int, payloads: MutableList<Any>) {
                super.onBindViewHolder(holder, position, payloads)
                val preference = getItem(position)
                if (preference is PreferenceCategory) {
                    setZeroPaddingToLayoutChildren(holder.itemView)
                } else {
                    holder.itemView.findViewById<View?>(R.id.icon_frame)?.visibility = if (preference.icon == null) View.GONE else View.VISIBLE
                }

            }
        }
    }

    private fun setZeroPaddingToLayoutChildren(view: View) {
        if (view !is ViewGroup) {
            return
        }
        for (i in 0 until view.childCount) {
            setZeroPaddingToLayoutChildren(view.getChildAt(i))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setPadding(0, view.paddingTop, view.paddingRight, view.paddingBottom)
            }
        }
    }
}