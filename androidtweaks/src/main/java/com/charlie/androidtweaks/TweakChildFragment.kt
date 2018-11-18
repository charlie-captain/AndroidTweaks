package com.charlie.androidtweaks

import android.os.Bundle
import androidx.preference.*

class TweakChildFragment : BasePreferenceFragment() {

    private lateinit var tweaks: ArrayList<Tweak>
    private var categorys: MutableSet<String>
    private lateinit var collection: String

    companion object {

        private val keyCollection = "collection"
        private val keyTweaks = "child_tweaks"

        fun newInstance(collection: String, tweaks: ArrayList<Tweak>): TweakChildFragment {
            val bundle = Bundle()
            bundle.putString(keyCollection, collection)
            bundle.putParcelableArrayList(keyTweaks, tweaks)
            val fragment = TweakChildFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    init {
        categorys = mutableSetOf()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        tweaks = arguments?.getParcelableArrayList<Tweak>(keyTweaks) as ArrayList<Tweak>
        collection = arguments?.getString(keyCollection)!!

        for (each in tweaks) {
            categorys.add(each.category)
        }

        for (c in categorys) {
            val category = PreferenceCategory(context)
            category.title = c
            screen.addPreference(category)
            for (t in tweaks) {
                if (t.category.equals(c)) {
                    when (t.type) {
                        TweakType.boolean -> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.title = t.title
                            switchPreference.setDefaultValue(t.defaultBoolValue)
                            category.addPreference(switchPreference)
                        }
                        TweakType.integer,
                        TweakType.double,
                        TweakType.float -> {
                            val seekBarPreference = SeekBarPreference(context)
                            seekBarPreference.title = t.title
                            seekBarPreference.max = t.maxIntValue
                            seekBarPreference.min = t.minIntValue
                            seekBarPreference.setDefaultValue(t.defaultIntValue)
                            seekBarPreference.setOnPreferenceChangeListener { preference, newValue ->

                                true
                            }
                            category.addPreference(seekBarPreference)
                        }

                    }

                }
            }
        }

//        val category1 = PreferenceCategory(context)
//        category1.title = "button"
//        category1.summary = "what is the problem"
//        category1.key = "bbb"
//
//        val swithButton = SwitchPreferenceCompat(context)
//        swithButton.title = "button"
//        swithButton.summary = "summary"
//        swithButton.key = "sss"
//
//        val swithButton2 = EditTextPreference(context)
//        swithButton2.title = "edit"
//
//        val swithButton3 = SeekBarPreference(context)
//        swithButton3.title = "seekbar"
//        swithButton3.value = 1
//
//
//        screen.addPreference(category1)
//        category1.addPreference(swithButton)
//
//        val category2 = PreferenceCategory(context)
//        category2.title = "category2"
//        screen.addPreference(category2)
//
//        category2.addPreference(swithButton2)
//        category2.addPreference(swithButton3)

        preferenceScreen = screen
    }


}
