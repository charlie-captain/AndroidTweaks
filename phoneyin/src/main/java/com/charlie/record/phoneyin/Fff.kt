package com.charlie.record.phoneyin

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

class  Fff : PreferenceFragmentCompat(){
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.preference_empty)
    }
}