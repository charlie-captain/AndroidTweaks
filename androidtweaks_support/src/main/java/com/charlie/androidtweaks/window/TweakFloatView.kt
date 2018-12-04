package com.charlie.androidtweaks.window

import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.ViewUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.utils.TweakUtil

class TweakFloatView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr), View.OnTouchListener {
    private var listener: OnViewLayoutParamsListener? = null

    private val statusBarHeight =
        TweakUtil.dimen(resources.getIdentifier("status_bar_height", "dimen", "android"), resources)

    private val screenWidth = TweakUtil.screenSize(resources).widthPixels

    private var startX = 0f

    private var startY = 0f

    private var isMove = false

    private var isShowCancel = false

    init {
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_launcher_round))
        setOnTouchListener(this)
    }

    fun setSize(width: Int) {
        val layout = ViewGroup.LayoutParams(width, width)
        layoutParams = layout
    }

    fun setWindowLayoutParams(listener: OnViewLayoutParamsListener) {
        this.listener = listener
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                isMove = false
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                listener?.onLayoutUpdate(event.rawX - startX, event.rawY - startY - statusBarHeight)
                if (isInDeleteArea(event))
                    if (!isShowCancel) {
                        isShowCancel = true
                        listener?.cancelAnimStart(true)
                    }
                isMove = true
                return true
            }
            MotionEvent.ACTION_UP -> {
                var finalX = 0f
                val layout = listener?.getLayoutParams()
                val upX = layout?.x?.plus(measuredWidth / 2f)
                if (upX!! >= screenWidth / 2f) {
                    finalX = (screenWidth - measuredWidth).toFloat()
                } else {
                    finalX = 0f
                }

                if (isShowCancel) {
                    isShowCancel = false
                    listener?.cancelAnimStart(false)
                }

                val anim =
                    ValueAnimator.ofFloat(layout.x.toFloat(), finalX).setDuration(Math.abs(layout.x - finalX).toLong())
                anim.interpolator = AccelerateDecelerateInterpolator()
                anim.addUpdateListener {
                    listener?.onLayoutUpdate(it.animatedValue as Float, layout.y.toFloat())
                }
                anim.start()

                return isMove
            }
        }
        return false
    }

    private fun isInDeleteArea(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y
        return false
    }

    private fun isRemoveAllView(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        val radius = Math.sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble())
        return radius <= TweakUtil.dp2px(
            (width * Math.sqrt(2.0) + TweakUtil.dimen(
                R.dimen.tweaks_width_cancel_radius,
                resources
            )).toFloat(), context.resources
        )
    }

    interface OnViewLayoutParamsListener {
        fun onLayoutUpdate(x: Float, y: Float)

        fun getLayoutParams(): WindowManager.LayoutParams?

        fun cancelAnimStart(appear: Boolean)
    }
}