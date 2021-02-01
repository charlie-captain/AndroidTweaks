package com.charlie.example.sample

import android.content.Context
import android.content.pm.ApplicationInfo

object Utils {

    fun isDebug(context: Context): Boolean {
        val value = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        return value != 0
    }
}