package com.charlie.androidtweaks.window

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.utils.TweakUtil

class TweakFloatCancelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.tweaks_color_cancel_view)
        isAntiAlias = true
    }

    private val screenSize = TweakUtil.screenSize(resources)
    private val mRadius = (screenSize.widthPixels / 2)
    private val mBigRadius = mRadius + 0.5f * mRadius
    var mCurrentRadius = mRadius

    private var mWidth = 0
    private var mHeight = 0


    fun setSize(width: Int) {
        val layoutParams = ViewGroup.LayoutParams(width, width)
        this.layoutParams = layoutParams
    }

    fun startScaleAnim(appear: Boolean) {
        val from = if (appear) mRadius else mBigRadius.toInt()
        val to = if (appear) mBigRadius.toInt() else mRadius
        val anim = ValueAnimator.ofInt(from, to)
        anim.duration = 300
        anim.interpolator = DecelerateInterpolator()
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            mCurrentRadius = value
            postInvalidate()
        }
        anim.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mWidth = width * 2
        mHeight = height * 2
    }

    fun startVisiable(appear: Boolean) {
        val from = if (appear) width * 2 else width
        val to = if (appear) width else width * 2
        val anim = ValueAnimator.ofInt(from, to)
        anim.duration = 300
        anim.interpolator = DecelerateInterpolator()
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            mWidth = value
            mHeight = value
            invalidate()
        }
        anim.start()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(
            mWidth.toFloat(),
            mHeight.toFloat(),
            mCurrentRadius.toFloat(),
            mPaint
        )
    }
}