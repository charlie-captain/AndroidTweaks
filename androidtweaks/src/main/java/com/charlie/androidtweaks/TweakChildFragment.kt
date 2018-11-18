package com.charlie.androidtweaks

import android.os.Bundle
import androidx.preference.*

class TweakChildFragment : BasePreferenceFragment() {

    private lateinit var tweaks: ArrayList<Tweak<TweakType>>
    private var categorys: MutableSet<String>
    private lateinit var collection: String

    companion object {
        private val keyCollection = "collection"
        private val keyTweaks = "child_tweaks"

        fun newInstance(collection: String, tweaks: ArrayList<Tweak<TweakType>>): TweakChildFragment {
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

        tweaks = arguments?.getParcelableArrayList<Tweak<TweakType>>(keyTweaks) as ArrayList<Tweak<TweakType>>
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
                            switchPreference.setDefaultValue(t.defaultValue)
                            category.addPreference(switchPreference)
                        }
                        TweakType.integer,
                        TweakType.double,
                        TweakType.float -> {
                            val seekBarPreference = SeekBarPreference(context)
                            seekBarPreference.title = t.title
                            seekBarPreference.max = t.maxValue as Int
                            seekBarPreference.min = t.minValue as Int
                            seekBarPreference.setDefaultValue(t.defaultValue)
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
