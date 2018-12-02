package com.charlie.androidtweaks.window

import android.content.Context

interface TweakWindowImp {

    fun showWindow(context: Context)

    fun dismissWindow()

    fun showPermission(context: Context)
}