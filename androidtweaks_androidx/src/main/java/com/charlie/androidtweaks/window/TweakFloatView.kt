package com.charlie.androidtweaks.window

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
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

    private var isInCancel = false

    private val screenSize = TweakUtil.screenSize(resources)

    private val touchSlop = 1

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
                isMove = true
                val distanceX = event.rawX - startX
                val distanceY = event.rawY - startY - statusBarHeight
                listener?.onLayoutUpdate(distanceX, distanceY)

                if (!isShowCancel) {
                    isShowCancel = true
                    listener?.cancelAnimVisiable(true)
                }
                if (isShowCancel && isInDeleteArea()) {
                    if (!isInCancel) {
                        isInCancel = true
                        listener?.cancelAnimStart(true)
                    }
                } else {
                    if (isInCancel) {
                        isInCancel = false
                        listener?.cancelAnimStart(false)
                    }
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                startX = 0f
                startY = 0f

                if (isShowCancel) {
                    if (isInCancel) {
                        isInCancel = false
                        listener?.removeAllView()
                    }
                    isShowCancel = false
                    listener?.cancelAnimVisiable(false)
                }

                val finalX: Float
                val layout = listener?.getLayoutParams()
                val upX = layout?.x?.plus(measuredWidth / 2f)
                if (upX!! >= screenWidth / 2f) {
                    finalX = (screenWidth - measuredWidth).toFloat()
                } else {
                    finalX = 0f
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

    private fun isInDeleteArea(): Boolean {
        val params = listener?.getLayoutParams()
        params ?: return false
        val centerX = params.x + width / 2f
        val centerY = params.y + height / 2f
        val distance = Math.sqrt(
            Math.pow((centerX - screenSize.widthPixels).toDouble(), 2.0) + Math.pow(
                (centerY - screenSize.heightPixels).toDouble(),
                2.0
            )
        )
        if (distance.toInt() <= listener?.cancelRadius()!!) {
            return true
        }
        return false
    }

    interface OnViewLayoutParamsListener {
        fun onLayoutUpdate(x: Float, y: Float)

        fun getLayoutParams(): WindowManager.LayoutParams?

        fun cancelAnimStart(appear: Boolean)

        fun cancelRadius(): Int

        fun cancelAnimVisiable(appear: Boolean)

        fun removeAllView()
    }

}