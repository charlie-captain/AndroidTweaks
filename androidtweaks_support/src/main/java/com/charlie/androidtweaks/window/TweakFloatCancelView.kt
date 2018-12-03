package com.charlie.androidtweaks.window

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.utils.TweakUtil

class TweakFloatCancelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var mPaint: Paint

    private var mRadius = TweakUtil.dimen(R.dimen.tweaks_width_cancel_radius, resources)
    private var mCurrentRadius: Float = 0f

    init {
        mPaint.apply {
            color = ContextCompat.getColor(context, R.color.tweaks_color_cancel_view)
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(
            width + mRadius - mCurrentRadius,
            height + mRadius - mCurrentRadius,
            mRadius.toFloat(),
            mPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mRadius * 2, mRadius * 2)

    }
}