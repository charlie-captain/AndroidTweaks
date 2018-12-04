package com.charlie.androidtweaks.window

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.utils.TweakUtil

class TweakFloatCancelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.tweaks_color_cancel_view)
        isAntiAlias = true
    }

    private var mRadius = TweakUtil.dimen(R.dimen.tweaks_width_cancel_radius, resources)
    private val mBigRadius = mRadius + 0.5f * mRadius
    private var mCurrentRadius = mRadius
    private val screenSize = TweakUtil.screenSize(resources)

    fun setSize(width: Int) {
        val layoutParams = ViewGroup.LayoutParams(width, width)
        this.layoutParams = layoutParams
    }

    fun startAnim(appear: Boolean) {
        val from = if (appear) mRadius else mBigRadius.toInt()
        val to = if (appear) mBigRadius.toInt() else mRadius
        val anim = ValueAnimator.ofInt(from, to)
        anim.duration = 300
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            mCurrentRadius = value
            postInvalidate()
        }
        anim.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(
            width.toFloat(),
            height.toFloat(),
            mCurrentRadius.toFloat(),
            mPaint
        )
    }
}