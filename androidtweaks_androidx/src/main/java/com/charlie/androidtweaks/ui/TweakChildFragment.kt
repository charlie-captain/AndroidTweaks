package com.charlie.androidtweaks.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.preference.*
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.*
import com.charlie.androidtweaks.view.TweakChangePreference
import java.math.BigDecimal
import java.util.*

class TweakChildFragment : TweakBaseFragment(), Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    private var allTweaks: ArrayList<Tweak<Any>>? = null
    private var tweaks: List<Tweak<Any>> = arrayListOf()
    private var tweakMap: HashMap<String, Tweak<Any>> = hashMapOf()

    /**
     * sort by positive
     */
    private var categories = TreeSet<String>()

    private var collection: String? = null

    var onTweakValueChangeListener: OnTweakValueChangeListener? = null

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

        allTweaks = TweakManager.getTweaks() as? ArrayList<Tweak<Any>> ?: arrayListOf()

        tweaks = allTweaks!!.filter {
            it.collection == collection
        }

        //sort category by name
        for (tweak in tweaks) {
            categories.add(tweak.category)
        }

        categories.forEach { category ->
            val preferenceCategory = PreferenceCategory(context)
            preferenceCategory.title = category
            screen.addPreference(preferenceCategory)

            var hasGroup: Boolean? = null
            var groupEnable = true
            var groupSwitchPreferenceKey: String? = null
            val groupList = ArrayList<Preference>()

            tweaks.forEach { tweak ->
                if (tweak.category == category) {
                    when (tweak.type) {
                        is TweakBool -> {
                            val switchPreference = SwitchPreferenceCompat(context)
                            switchPreference.isPersistent = false
                            switchPreference.title = tweak.title
                            switchPreference.key = tweak.toString()
                            switchPreference.isChecked = tweak.value as Boolean
                            switchPreference.onPreferenceChangeListener = this
                            if (hasGroup == null && tweak.type.isGroup) {
                                //make this switch preference be first
                                hasGroup = tweak.type.isGroup
                                groupEnable = tweak.value as Boolean
                                groupSwitchPreferenceKey = switchPreference.key
                                groupList.add(0, switchPreference)
                            } else {
                                groupList.add(switchPreference)
                            }
                        }
                        is TweakFloat,
                        is TweakDouble -> {
                            val changePreference = TweakChangePreference(context)
                            changePreference.isPersistent = false
                            changePreference.title = tweak.title
                            changePreference.key = tweak.toString()
                            if (tweak.type is TweakFloat) {
                                //change float to double
                                changePreference.mMax = BigDecimal(tweak.type.max.toString()).toDouble()
                                changePreference.mMin = BigDecimal(tweak.type.min.toString()).toDouble()
                                changePreference.mIncrement = BigDecimal(tweak.type.increment.toString()).toDouble()
                                changePreference.mIsFloat = true
                                changePreference.setValue(BigDecimal(tweak.value.toString()).toDouble())
                            } else if (tweak.type is TweakDouble) {
                                changePreference.mMax = tweak.type.max
                                changePreference.mMin = tweak.type.min
                                changePreference.mIncrement = tweak.type.increment
                                changePreference.setValue(tweak.value as Double)
                            }
                            changePreference.onPreferenceChangeListener = this
                            changePreference.onPreferenceClickListener = this
                            groupList.add(changePreference)
                        }
                        is TweakString -> {
                            val editTextPreference = EditTextPreference(context)
                            editTextPreference.isPersistent = false
                            editTextPreference.title = tweak.title
                            editTextPreference.dialogTitle = tweak.title
                            editTextPreference.text = tweak.value.toString()
                            editTextPreference.summary = tweak.value.toString()
                            editTextPreference.key = tweak.toString()
                            editTextPreference.onPreferenceChangeListener = this
                            groupList.add(editTextPreference)
                        }
                        else -> {
                            throw IllegalArgumentException(EXCEPTION_ILLEGAL_ARGUMENT)
                        }
                    }
                    tweakMap[tweak.toString()] = tweak
                }
            }
            groupList.forEach { preference ->
                preferenceCategory.addPreference(preference)
            }
            //if has group setting, it should be updated
            if (hasGroup == true) {
                updateGroupEnable(preferenceCategory, groupSwitchPreferenceKey, groupEnable)
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
                    preference.setValue(it.toDouble())
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
        val tweak = tweakMap[key] ?: return false
        tweak.value = newValue!!
        tweak.callback?.let { callback ->
            callback(requireContext(), tweak)
        }
        onTweakValueChangeListener?.onTweakValueChange(tweak)
        when (preference) {
            is EditTextPreference -> {
                //update value
                preference.summary = newValue.toString()
            }
            is SwitchPreferenceCompat -> {
                val enable = newValue as? Boolean ?: false
                if (tweak.type is TweakBool && tweak.type.isGroup) {
                    updateGroupEnable(preference.parent, preference.key, enable)
                }
            }
        }
        return true
    }

    /**
     * update group enable state
     */
    private fun updateGroupEnable(preference: PreferenceGroup?, key: String?, newValue: Boolean) {
        preference?.let {
            for (index in 0 until it.preferenceCount) {
                val childPreference = it.getPreference(index)
                if (childPreference.key != key) {
                    childPreference.isEnabled = newValue
                }
            }
        }
    }

    interface OnTweakValueChangeListener {
        fun <T> onTweakValueChange(tweak: Tweak<T>)
    }
}
