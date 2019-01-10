package com.charlie.androidtweaks.data

import android.os.Parcel
import android.os.Parcelable
import com.charlie.androidtweaks.utils.TweakValueDelegate

/**
 * Tweak
 */
data class Tweak(
    val collection: String,
    val category: String,
    val title: String,
    val type: TweakType
) : Parcelable {

    internal var value: Any by TweakValueDelegate(toString(), type.getValue())

    var boolValue: Boolean = false
        get() {
            return value as? Boolean ?: field
        }
        private set

    var floatValue: Float = 0f
        get() {
            return value as? Float ?: field

        }
        private set

    var stringValue: String = String()
        get() {
            return value as? String ?: field
        }
        private set

    /**
     * reset the value to default
     */
    fun reset() {
        value = type.getDefault()
    }

    override fun toString(): String {

        return "${collection}_${category}_$title"
    }

    /* parcelable start*/
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readSerializable() as TweakType
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(collection)
        writeString(category)
        writeString(title)
        writeSerializable(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Tweak> = object : Parcelable.Creator<Tweak> {
            override fun createFromParcel(source: Parcel): Tweak = Tweak(source)
            override fun newArray(size: Int): Array<Tweak?> = arrayOfNulls(size)
        }
    }
    /* parcelable end*/

}
