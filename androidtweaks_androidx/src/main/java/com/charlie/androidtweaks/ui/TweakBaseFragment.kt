package com.charlie.androidtweaks.ui

import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

/**
 * fix left padding bug
 */
abstract class TweakBaseFragment : PreferenceFragmentCompat() {

    //fix the left padding
    override fun setPreferenceScreen(preferenceScreen: PreferenceScreen?) {
        super.setPreferenceScreen(preferenceScreen)
        if (preferenceScreen != null) {
            val count = preferenceScreen.preferenceCount
            for (i in 0 until count) {
                if (preferenceScreen.getPreference(i) is PreferenceCategory) {
                    val category = preferenceScreen.getPreference(i) as PreferenceCategory
                    val childCount = category.preferenceCount
                    for (j in 0 until childCount) {
                        category.getPreference(j).isIconSpaceReserved = false
                    }
                }
                preferenceScreen.getPreference(i).isIconSpaceReserved = false
            }
        }
    }
}