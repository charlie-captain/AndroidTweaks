package com.charlie.androidtweaks.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.SwitchPreferenceCompat
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.*
import com.charlie.androidtweaks.view.TweakChangePreference
import java.util.*


class TweakChildFragment : TweakBaseFragment(), Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener {
    private var allTweaks: ArrayList<Tweak>? = null
    private var tweaks: List<Tweak> = arrayListOf()
    private var tweakMap: HashMap<String, Tweak> = hashMapOf()

    private var categories: TreeSet<String> = TreeSet()
    private var collection: String? = null

    companion object {
        private const val KEY_COLLECTION = "tweaks_collection"

        fun newInstance(collection: String) =
            TweakChildFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_COLLECTION, collection)
                }
            }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        collection = arguments?.getString(KEY_COLLECTION) ?: ""

        allTweaks = TweakManager.getTweaks() ?: arrayListOf()

        tweaks = allTweaks!!.filter {
            it.collection == collection
        }

        for (tweak in tweaks) {
            categories.add(tweak.category)
        }

        for (c in categories) {
            val category = PreferenceCategory(context)
            category.title = c
            screen.addPreference(category)
            for (tweak in tweaks) {
                if (tweak.category == c) {
                    when (tweak.type) {
                        is TweakBool -> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.isPersistent = false
                            switchPreference.title = tweak.title
                            switchPreference.key = tweak.toString()
                            switchPreference.isChecked = tweak.value as Boolean
                            switchPreference.onPreferenceChangeListener = this
                            category.addPreference(switchPreference)
                        }
                        is TweakFloat -> {
                            val changePreference = TweakChangePreference(context)
                            changePreference.isPersistent = false
                            changePreference.title = tweak.title
                            changePreference.key = tweak.toString()
                            changePreference.mMax = tweak.type.max
                            changePreference.mMin = tweak.type.min
                            changePreference.mIncrement = tweak.type.increment
                            changePreference.setValue(tweak.floatValue)
                            changePreference.onPreferenceChangeListener = this
                            changePreference.onPreferenceClickListener = this
                            category.addPreference(changePreference)
                        }
                        is TweakString -> {
                            val editTextPreference = EditTextPreference(context)
                            editTextPreference.isPersistent = false
                            editTextPreference.title = tweak.title
                            editTextPreference.dialogTitle = tweak.title
                            editTextPreference.text = tweak.value as? String
                            editTextPreference.summary = tweak.value as? String
                            editTextPreference.key = tweak.toString()
                            editTextPreference.onPreferenceChangeListener = this
                            category.addPreference(editTextPreference)
                        }
                        else -> {
                            throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                        }
                    }
                    tweakMap[tweak.toString()] = tweak
                }
            }
        }
        preferenceScreen = screen
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference) {
            is TweakChangePreference -> {
                showChangeEditTextDialog(preference)
            }
        }
        return true
    }

    /**
     * show edit-text dialog
     */
    private fun showChangeEditTextDialog(preference: TweakChangePreference?) {
        preference ?: return
        val view = View.inflate(context, R.layout.tweaks_dialog_edit_text, null)
        val editText = view.findViewById<EditText>(R.id.tweaks_edit_text)
        editText.setText(preference.mValue.toString())
        AlertDialog.Builder(context).setView(view)
            .setTitle(preference.title)
            .setCancelable(true)
            .setPositiveButton("yes") { _, _ ->
                val value = editText.text.toString().toFloatOrNull()
                value?.let {
                    preference.setValue(it)
                }
            }.setNegativeButton("no") { dialog, _ ->
                dialog.dismiss()
            }.show()
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
        if (preference is EditTextPreference) {
            //update value
            preference.summary = newValue?.toString()
        }

        return true
    }
}
