package com.charlie.androidtweaks

import android.os.Bundle
import androidx.preference.PreferenceCategory
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreferenceCompat

class TweakChildFragment : BaseFragment() {

    private lateinit var tweaks: ArrayList<Tweak>
    private var categorys: MutableSet<String>
    private lateinit var collection: String

    companion object {
        private val keyCollection = "collection"
        private val keyTweaks = "child_tweaks"

        fun newInstance(collection: String, tweaks: ArrayList<Tweak>): TweakChildFragment {
            val bundle = Bundle()
            bundle.putString(keyCollection, collection)
            bundle.putSerializable(keyTweaks, tweaks)
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

        tweaks = arguments?.getSerializable(keyTweaks) as ArrayList<Tweak>
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
                        TweakViewDataType.boolean -> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.title = t.title
                            switchPreference.setDefaultValue(t.defaultBoolValue)
                            category.addPreference(switchPreference)
                        }
                        TweakViewDataType.integer,
                        TweakViewDataType.double,
                        TweakViewDataType.cgFloat -> {
                            val seekBarPreference = SeekBarPreference(context)
                            seekBarPreference.title = t.title
                            seekBarPreference.max = t.maxIntValue as Int
                            seekBarPreference.min = t.minIntValue as Int
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
        preferenceScreen = screen
    }


}
