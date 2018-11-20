package com.charlie.androidtweaks.view

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import androidx.preference.R

class TweakSeekbarPrefence : Preference {


    private val TAG = "SeekBarPreference"
    internal /* synthetic access */ var mSeekBarValue: Int = 0
    var min: Int = 0
        set(value) {
            if (value != field) {
                field = value
                notifyChanged()
            }
        }
    var max: Int = 0
        set(value) {
            if (value != field) {
                field = value
                notifyChanged()
            }
        }
    var mSeekBarIncrement: Int = 0
    internal /* synthetic access */ var mTrackingTouch: Boolean = false
    internal /* synthetic access */ var mSeekBar: SeekBar? = null
    private var mSeekBarValueTextView: TextView? = null
    // Whether the SeekBar should respond to the left/right keys
    internal /* synthetic access */ var mAdjustable: Boolean = false
    // Whether to show the SeekBar value TextView next to the bar
    var mShowSeekBarValue: Boolean = false
    /**
     * Listener reacting to the [SeekBar] changing value by the user
     */
    private val mSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                setValueInternal(progress, false)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            syncValueInternal(seekBar)
        }
    }

    /**
     * Listener reacting to the user pressing DPAD left/right keys if `adjustable` attribute is set to true; it transfers the key presses to the [SeekBar]
     * to be handled accordingly.
     */
    private val mSeekBarKeyListener = View.OnKeyListener { v, keyCode, event ->
        if (event.action != KeyEvent.ACTION_DOWN) {
            return@OnKeyListener false
        }

        if (!mAdjustable && (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
            // Right or left keys are pressed when in non-adjustable mode; Skip the keys.
            return@OnKeyListener false
        }

        // We don't want to propagate the click keys down to the SeekBar view since it will
        // create the ripple effect for the thumb.
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            return@OnKeyListener false
        }

        if (mSeekBar == null) {
            Log.e(TAG, "SeekBar view is null and hence cannot be adjusted.")
            return@OnKeyListener false
        }
        mSeekBar!!.onKeyDown(keyCode, event)
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.SeekBarPreference, defStyleAttr, defStyleRes
        )

        // The ordering of these two statements are important. If we want to set max first, we need
        // to perform the same steps by changing min/max to max/min as following:
        // max = a.getInt(...) and setMin(...).
        min = a.getInt(R.styleable.SeekBarPreference_min, 0)
        max = a.getInt(R.styleable.SeekBarPreference_android_max, 100)
        setSeekBarIncrement(a.getInt(R.styleable.SeekBarPreference_seekBarIncrement, 0))
        mAdjustable = a.getBoolean(R.styleable.SeekBarPreference_adjustable, true)
        mShowSeekBarValue = a.getBoolean(R.styleable.SeekBarPreference_showSeekBarValue, true)
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.seekBarPreferenceStyle)


    constructor(context: Context) : this(context, null)


    override fun onBindViewHolder(view: PreferenceViewHolder) {
        super.onBindViewHolder(view)
        view.itemView.setOnKeyListener(mSeekBarKeyListener)
        mSeekBar = view.findViewById(R.id.seekbar) as SeekBar
        mSeekBarValueTextView = view.findViewById(R.id.seekbar_value) as TextView
        if (mShowSeekBarValue) {
            mSeekBarValueTextView!!.visibility = View.VISIBLE
        } else {
            mSeekBarValueTextView!!.visibility = View.GONE
            mSeekBarValueTextView = null
        }

        if (mSeekBar == null) {
            Log.e(TAG, "SeekBar view is null in onBindViewHolder.")
            return
        }
        mSeekBar!!.setOnSeekBarChangeListener(mSeekBarChangeListener)
        mSeekBar!!.max = max - min
        // If the increment is not zero, use that. Otherwise, use the default mKeyProgressIncrement
        // in AbsSeekBar when it's zero. This default increment value is set by AbsSeekBar
        // after calling setMax. That's why it's important to call setKeyProgressIncrement after
        // calling setMax() since setMax() can change the increment value.
        if (mSeekBarIncrement != 0) {
            mSeekBar!!.keyProgressIncrement = mSeekBarIncrement
        } else {
            mSeekBarIncrement = mSeekBar!!.keyProgressIncrement
        }

        mSeekBar!!.progress = mSeekBarValue - min
        if (mSeekBarValueTextView != null) {
            mSeekBarValueTextView!!.text = mSeekBarValue.toString()
        }
        mSeekBar!!.isEnabled = isEnabled
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        var defaultValue = defaultValue
        if (defaultValue == null) {
            defaultValue = 0
        }
        setValue(getPersistedInt((defaultValue as Int?)!!))
    }

    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any {
        return a!!.getInt(index, 0)
    }

    /**
     * Returns the amount of increment change via each arrow key click. This value is derived from
     * user's specified increment value if it's not zero. Otherwise, the default value is picked
     * from the default mKeyProgressIncrement value in [android.widget.AbsSeekBar].
     *
     * @return The amount of increment on the [SeekBar] performed after each user's arrow
     * key press
     */
    fun getSeekBarIncrement(): Int {
        return mSeekBarIncrement
    }

    /**
     * Sets the increment amount on the [SeekBar] for each arrow key press.
     *
     * @param seekBarIncrement The amount to increment or decrement when the user presses an
     * arrow key.
     */
    fun setSeekBarIncrement(seekBarIncrement: Int) {
        if (seekBarIncrement != mSeekBarIncrement) {
            mSeekBarIncrement = Math.min(max - min, Math.abs(seekBarIncrement))
            notifyChanged()
        }
    }


    /**
     * Gets whether the [SeekBar] should respond to the left/right keys.
     *
     * @return Whether the [SeekBar] should respond to the left/right keys
     */
    fun isAdjustable(): Boolean {
        return mAdjustable
    }

    /**
     * Sets whether the [SeekBar] should respond to the left/right keys.
     *
     * @param adjustable Whether the [SeekBar] should respond to the left/right keys
     */
    fun setAdjustable(adjustable: Boolean) {
        mAdjustable = adjustable
    }

    private fun setValueInternal(seekBarValue: Int, notifyChanged: Boolean) {
        var seekBarValue = seekBarValue
        if (seekBarValue < min) {
            seekBarValue = min
        }
        if (seekBarValue > max) {
            seekBarValue = max
        }

        if (seekBarValue != mSeekBarValue) {
            mSeekBarValue = seekBarValue
            if (mSeekBarValueTextView != null) {
                mSeekBarValueTextView!!.text = mSeekBarValue.toString()
            }
            persistInt(seekBarValue)
            if (notifyChanged) {
                notifyChanged()
            }
        }
    }

    /**
     * Gets the current progress of the [SeekBar].
     *
     * @return The current progress of the [SeekBar]
     */
    fun getValue(): Int {
        return mSeekBarValue
    }

    /**
     * Sets the current progress of the [SeekBar].
     *
     * @param seekBarValue The current progress of the [SeekBar]
     */
    fun setValue(seekBarValue: Int) {
        setValueInternal(seekBarValue, true)
    }

    /**
     * Persist the [SeekBar]'s SeekBar value if callChangeListener returns true, otherwise
     * set the [SeekBar]'s value to the stored value.
     */
    internal /* synthetic access */ fun syncValueInternal(seekBar: SeekBar) {
        val seekBarValue = min + seekBar.progress
        if (callChangeListener(seekBarValue)) {
            setValueInternal(seekBarValue, false)
        } else {
            seekBar.progress = mSeekBarValue - min
        }
    }


    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        if (isPersistent) {
            // No need to save instance state since it's persistent
            return superState
        }

        // Save the instance state
        val myState = SavedState(superState)
        myState.mSeekBarValue = mSeekBarValue
        myState.mMin = min
        myState.mMax = max
        return myState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state!!.javaClass != SavedState::class.java) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state)
            return
        }

        // Restore the instance state
        val myState = state as SavedState?
        super.onRestoreInstanceState(myState!!.superState)
        mSeekBarValue = myState.mSeekBarValue
        min = myState.mMin
        max = myState.mMax
        notifyChanged()
    }

    /**
     * SavedState, a subclass of [BaseSavedState], will store the state of this preference.
     *
     *
     * It is important to always call through to super methods.
     */
    private class SavedState : Preference.BaseSavedState {

        internal var mSeekBarValue: Int = 0
        internal var mMin: Int = 0
        internal var mMax: Int = 0

        internal constructor(source: Parcel) : super(source) {

            // Restore the click counter
            mSeekBarValue = source.readInt()
            mMin = source.readInt()
            mMax = source.readInt()
        }

        internal constructor(superState: Parcelable) : super(superState) {}

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)

            // Save the click counter
            dest.writeInt(mSeekBarValue)
            dest.writeInt(mMin)
            dest.writeInt(mMax)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}