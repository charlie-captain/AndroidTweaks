package com.charlie.androidtweaks.ui

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.SwitchPreferenceCompat
import com.charlie.androidtweaks.data.*
import com.charlie.androidtweaks.view.TweakSeekbarPrefence

private const val KEY_COLLECTIONS = "collection"
private const val KEY_TWEAKS = "child_tweaks"

class TweakChildFragment : TweakBaseFragment(), Preference.OnPreferenceChangeListener {
    private lateinit var tweaks: ArrayList<Tweak>

    private var tweakMap: HashMap<String, Tweak> = hashMapOf()

    private var categorys: HashSet<String> = hashSetOf()
    private lateinit var collection: String

    companion object {

        fun newInstance(collection: String, tweaks: ArrayList<Tweak>) =
                TweakChildFragment().apply {
                    arguments = Bundle().apply {
                        putString(KEY_COLLECTIONS, collection)
                        putSerializable(KEY_TWEAKS, tweaks)
                    }
                }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        tweaks = arguments?.getSerializable(KEY_TWEAKS) as ArrayList<Tweak>
        collection = arguments?.getString(KEY_COLLECTIONS)!!

        for (each in tweaks) {
            categorys.add(each.category)
        }

        for (c in categorys) {
            val category = PreferenceCategory(context)
            category.title = c
            screen.addPreference(category)
            for (t in tweaks) {
                if (t.category == c) {
                    when (t.type) {
                        is TweakBool-> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.isPersistent = false
                            switchPreference.title = t.title
                            switchPreference.key = t.toString()
                            switchPreference.isChecked = t.value as Boolean
                            switchPreference.onPreferenceChangeListener = this
                            category.addPreference(switchPreference)
                        }
                        is TweakInt-> {
                            val seekBarPreference = TweakSeekbarPrefence(context)
                            seekBarPreference.isPersistent = false
                            seekBarPreference.title = t.title
                            seekBarPreference.max = t.type.max
                            seekBarPreference.min = t.type.min
                            seekBarPreference.key = t.toString()
                            seekBarPreference.setValue(t.value as Int)
                            seekBarPreference.onPreferenceChangeListener = this
                            category.addPreference(seekBarPreference)
                        }
                        is TweakString->{
                            val editTextPreference = EditTextPreference(context)
                            editTextPreference.isPersistent = false
                            editTextPreference.title = t.title
                            editTextPreference.dialogTitle = t.title
                            editTextPreference.text = t.value as? String
                            editTextPreference.summary = t.value as? String
                            editTextPreference.key = t.toString()
                            editTextPreference.onPreferenceChangeListener = this
                            category.addPreference(editTextPreference)
                        }
                        else -> {
                            throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                        }
                    }
                    tweakMap[t.toString()] = t
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
                tweakMap[key]?.let {
                    it.value = newValue!!
                }
            }
        }


        return true
    }
}
