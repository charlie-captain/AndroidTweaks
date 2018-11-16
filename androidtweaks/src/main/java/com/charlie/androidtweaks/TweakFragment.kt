package com.charlie.androidtweaks

import android.os.Bundle
import android.support.v7.preference.*


class TweakFragment : PreferenceFragmentCompat() {


    private var category: ArrayList<Tweak>? = null


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
//        addPreferencesFromResource(R.xml.preference)

        val screen = preferenceManager.createPreferenceScreen(context)

        val category1 = PreferenceCategory(context)
        category1.title = "button"
        category1.summary = "what is the problem"
        category1.key = "bbb"

        val swithButton = SwitchPreferenceCompat(context)
        swithButton.title = "button"
        swithButton.summary = "summary"
        swithButton.key = "sss"

        val swithButton2 = EditTextPreference(context)
        swithButton2.title = "edit"

        val swithButton3 = SeekBarPreference(context)
        swithButton3.title = "seekbar"
        swithButton3.value = 1


        screen.addPreference(category1)
        category1.addPreference(swithButton)

        val category2 = PreferenceCategory(context)
        category2.title = "category2"

        screen.addPreference(category2)

        category2.addPreference(swithButton2)
        category2.addPreference(swithButton3)
        preferenceScreen = screen

    }


}