package com.charlie.androidtweaks

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreferenceCompat

class TweakChildFragment : BaseFragment(), Preference.OnPreferenceChangeListener {
    private lateinit var tweaks: ArrayList<Tweak>

    private lateinit var tweakMap: MutableMap<String, Tweak>
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
        tweakMap = mutableMapOf()
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
                var key = ""
                if (t.category.equals(c)) {
                    when (t.type) {
                        TweakViewDataType.boolean -> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.title = t.title
                            switchPreference.setDefaultValue(t.defaultBoolValue)
                            key = "${t.collection}_${t.category}_${t.title}"
                            switchPreference.key = key
                            switchPreference.setOnPreferenceChangeListener(this)
                            category.addPreference(switchPreference)
                        }
                        TweakViewDataType.integer,
                        TweakViewDataType.double,
                        TweakViewDataType.cgFloat -> {
                            val seekBarPreference = SeekBarPreference(context)
                            seekBarPreference.title = t.title
                            seekBarPreference.max = t.maxIntValue
                            seekBarPreference.min = t.minIntValue
                            seekBarPreference.setDefaultValue(t.defaultIntValue)
                            key = "${t.collection}_${t.category}_${t.title}"
                            seekBarPreference.key = key
                            seekBarPreference.setOnPreferenceChangeListener(this)
                            category.addPreference(seekBarPreference)
                        }
                    }
                    tweakMap.put(key, t)
                }
            }
        }
        preferenceScreen = screen
    }

    /**
     * SaveTweaks
     */
    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val key = preference!!.key
        tweakMap.containsKey(key).let {
            if (it) {
                tweakMap.get(key)?.let {
                    val default = when (it.type) {
                        TweakViewDataType.boolean -> it.defaultBoolValue
                        TweakViewDataType.integer -> it.defaultIntValue
                        else -> {
                            throw IllegalArgumentException(TweakConstant.EXCEPTION_ILLEGAL_ARGUMENT)
                        }
                    }
                    var putValue by TweakSharepreference(key, default)
                    putValue = newValue!!
                }
            }
        }


        return true
    }


}
