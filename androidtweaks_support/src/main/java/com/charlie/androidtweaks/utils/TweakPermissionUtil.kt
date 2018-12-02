package com.charlie.androidtweaks.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

const val TWEAK_REQUESET_CODE_PERMIISION = 109

object TweakPermissionUtil {

    fun checkPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context)
        }
        return true
    }


    fun applyPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (context as Activity).startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.packageName)
                ),
                TWEAK_REQUESET_CODE_PERMIISION
            )
        }
    }

    fun onActivityResult(
        context: Context,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        listener: OnPermissionListener
    ) {
        if (requestCode == TWEAK_REQUESET_CODE_PERMIISION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                listener.onPermissionGranted(Settings.canDrawOverlays(context))
            }
        }
    }

    interface OnPermissionListener {
        fun onPermissionGranted(isGranted: Boolean)
    }

}