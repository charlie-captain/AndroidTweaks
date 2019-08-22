package com.charlie.androidtweaks.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TweakMenuItem(val itemId: Int, val title: String, val groupId: Int? = null) : Parcelable