package com.charlie.androidtweaks.view

import android.content.Context
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceViewHolder
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.charlie.androidtweaks.R
import java.math.BigDecimal

/**
 * @author: Charlie
 */
class TweakChangePreference : Preference, View.OnClickListener {
    private var mValueTextView: TextView? = null
    private var mAddButton: ImageButton? = null
    private var mMinusButton: ImageButton? = null

    internal var mMin: Float = 0f
        set(min) {
            if (min != field && min <= mMax) {
                field = min
                notifyChanged()
            }
        }

    internal var mMax: Float = 100f
        set(max) {
            if (max != field && max >= field) {
                field = max
                notifyChanged()
            }
        }

    internal var mIncrement: Float = 1f
        set(increment) {
            if (increment != field) {
                field = increment
                notifyChanged()
            }
        }

    internal var mValue: Float = 0f

    /**
     * 是否为负数
     * 默认 ： 可以负数
     */
    private var isNegative = true


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributes: AttributeSet?) : this(context, attributes, 0)

    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int) : super(context, attributes, defStyleAttr) {

        widgetLayoutResource = R.layout.tweak_preference_change

    }


    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val titleView = holder?.findViewById(android.R.id.title) as TextView?
        titleView?.textSize = 16f

        mValueTextView = holder?.findViewById(R.id.tweaks_change_value) as TextView?
        mAddButton = holder?.findViewById(R.id.tweaks_change_add) as ImageButton?
        mMinusButton = holder?.findViewById(R.id.tweaks_change_minus) as ImageButton?

        mAddButton?.setOnClickListener(this)
        mMinusButton?.setOnClickListener(this)

        mValueTextView?.text = "$mValue"
    }


    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            R.id.tweaks_change_add -> {

                val newValue = BigDecimal(mValue.toString()) + BigDecimal(mIncrement.toString())
                setValue(newValue.toFloat())
            }
            R.id.tweaks_change_minus -> {
                val newValue = BigDecimal(mValue.toString()) - BigDecimal(mIncrement.toString())
                setValue(newValue.toFloat())
            }
        }
    }

    fun setValue(value: Float) {
        if (mValue != value) {
            if (!isNegative && value < 0) {
                //不可为负数
                return
            }
            if (value < mMin || value > mMax) {
                //排除范围
                return
            }
            mValue = value
            mValueTextView?.let {
                it.text = "$mValue"
            }
            callChangeListener(mValue)
        }
    }
}
