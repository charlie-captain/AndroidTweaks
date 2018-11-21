package com.charlie.androidtweaks.ui

import android.os.Bundle
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceCategory
import android.support.v7.preference.SwitchPreferenceCompat
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.data.EXCEPTION_ILLEGAL_ARGUMENT
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakViewDataType
import com.charlie.androidtweaks.utils.TweakSharePreferenceUtil
import com.charlie.androidtweaks.view.TweakSeekbarPrefence
import kotlinx.android.synthetic.main.tweaks_dialog_int.*

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
                var key = "${t.collection}_${t.category}_${t.title}"
                if (t.category.equals(c)) {
                    when (t.type) {
                        TweakViewDataType.boolean -> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.isPersistent = false
                            switchPreference.title = t.title
                            switchPreference.key = key
                            val value by TweakSharePreferenceUtil(key, t.defaultBoolValue)
                            switchPreference.isChecked = value
                            switchPreference.onPreferenceChangeListener = this
                            category.addPreference(switchPreference)
                        }
                        TweakViewDataType.integer -> {
                            val seekBarPreference = TweakSeekbarPrefence(context)
                            seekBarPreference.isPersistent = false
                            seekBarPreference.title = t.title
                            seekBarPreference.max = t.maxIntValue
                            seekBarPreference.min = t.minIntValue
                            seekBarPreference.key = key
                            val value by TweakSharePreferenceUtil(key, t.defaultIntValue)
                            seekBarPreference.setValue(value)
                            seekBarPreference.onPreferenceChangeListener = this
                            category.addPreference(seekBarPreference)
                        }
                        TweakViewDataType.string -> {
                            val editTextPreference = EditTextPreference(context)
                            editTextPreference.isPersistent = false
                            editTextPreference.title = t.title
                            editTextPreference.key = key
                            val value by TweakSharePreferenceUtil(key, t.defaultStringValue)
                            editTextPreference.text = value
                            editTextPreference.summary = value
                            editTextPreference.dialogTitle = "putString"
                            editTextPreference.onPreferenceChangeListener = this
                            category.addPreference(editTextPreference)
                        }
                        TweakViewDataType.intergerEdit -> {
                            val editTextPreference = EditTextPreference(context)
                            editTextPreference.isPersistent = false
                            editTextPreference.title = t.title
                            editTextPreference.key = key
                            val value by TweakSharePreferenceUtil(key, t.defaultIntValue)
                            editTextPreference.dialogLayoutResource = R.layout.tweaks_dialog_int
                            tweaks_dialog_title.text = "putInt"
                            tweaks_dialog_edittext.setText(value)
                            editTextPreference.summary = value.toString()
                            editTextPreference.onPreferenceChangeListener = this
                            category.addPreference(editTextPreference)
                        }
                        else -> {

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
                tweakMap[key]?.let {
                    val default = when (it.type) {
                        TweakViewDataType.boolean -> it.defaultBoolValue
                        TweakViewDataType.integer, TweakViewDataType.intergerEdit -> it.defaultIntValue
                        TweakViewDataType.string -> it.defaultStringValue
                        else -> {
                            throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                        }
                    }
                    var putValue by TweakSharePreferenceUtil(key, default)
                    putValue = newValue!!
                }
            }
        }


        return true
    }
}
