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

    private var mIncrement: Float = 1f

    private var mValue: Float = 0f

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

    fun setIncrement(increment: Float) {
        if (increment != mIncrement) {
            mIncrement = increment
            notifyChanged()
        }
    }

    fun setNegetive(isNegative: Boolean) {
        this.isNegative = isNegative
    }


    fun setValue(value: Float) {
        if (mValue != value) {
            if (!isNegative && value < 0) {
                //不可为负数
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
