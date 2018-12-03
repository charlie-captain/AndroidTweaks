package com.charlie.androidtweaks.window

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.WindowManager
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_KEY
import com.charlie.androidtweaks.data.SP_TWEAKS_FLOAT_WINDOW_LAYOUT_KEY
import com.charlie.androidtweaks.ui.TweakActivity
import com.charlie.androidtweaks.utils.TweakSharePreferenceUtil
import com.charlie.androidtweaks.utils.TweakUtil

object TweakWindowManager : TweakWindowImp, TweakFloatView.OnViewLayoutParamsListener {
    private var mWindowManager: WindowManager? = null

    private var mWindowLayoutParams: WindowManager.LayoutParams? = null
    private var mCancelWindowLayoutParams: WindowManager.LayoutParams? = null


    private var mView: TweakFloatView? = null

    private var mCancelView: TweakFloatCancelView? = null

    private var mWidth: Int = 0

    private var mCancelWidth: Int = 0

    override fun showPermission(context: Context) {
        showWindow(context)
    }


    override fun showWindow(context: Context) {
        if (mWindowManager == null && mView == null && mCancelView == null) {
            mWidth = TweakUtil.dimen(R.dimen.tweaks_width_float_window, context.resources)
            mCancelWidth = TweakUtil.dimen(R.dimen.tweaks_width_cancel_radius, context.resources)
            mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            mView = TweakFloatView(context)
            mView?.setSize(mWidth)
            mView?.setWindowLayoutParams(this)
            mCancelView = TweakFloatCancelView(context)
            initListener(context)
            mWindowLayoutParams = WindowManager.LayoutParams()
            mCancelWindowLayoutParams = WindowManager.LayoutParams()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mWindowLayoutParams?.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                mCancelWindowLayoutParams?.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                mWindowLayoutParams?.type = WindowManager.LayoutParams.TYPE_PHONE
                mCancelWindowLayoutParams?.type = WindowManager.LayoutParams.TYPE_PHONE
            }
            mWindowLayoutParams?.format = PixelFormat.RGBA_8888
            mWindowLayoutParams?.gravity = Gravity.START or Gravity.TOP
            mWindowLayoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            mWindowLayoutParams?.width = TweakUtil.dp2px(mWidth.toFloat(), context.resources)
            mWindowLayoutParams?.height = TweakUtil.dp2px(mWidth.toFloat(), context.resources)
            val xAndy by TweakSharePreferenceUtil(SP_TWEAKS_FLOAT_WINDOW_LAYOUT_KEY, "")
            if (!TextUtils.isEmpty(xAndy)) {
                val xAndyList = xAndy.split(",")
                mWindowLayoutParams?.x = xAndyList[0].toInt()
                mWindowLayoutParams?.y = xAndyList[1].toInt()
            }
            mWindowManager?.addView(mView, mWindowLayoutParams)


            mCancelWindowLayoutParams?.format = PixelFormat.RGBA_8888
            mCancelWindowLayoutParams?.gravity = Gravity.END or Gravity.BOTTOM
            mCancelWindowLayoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            mCancelWindowLayoutParams?.width = mCancelWidth
            mCancelWindowLayoutParams?.height = mCancelWidth
            mWindowManager?.addView(mCancelView, mCancelWindowLayoutParams)
        }
    }

    private fun initListener(context: Context) {
        mView?.setOnClickListener {
            val getValue by TweakSharePreferenceUtil(SP_TWEAKS_FLOAT_WINDOW_KEY, "")
            TweakActivity.start(context, getValue)
        }
    }


    override fun dismissWindow() {
        if (mWindowManager != null && mView != null) {
            mWindowManager?.removeViewImmediate(mView)
//            mWindowManager?.removeViewImmediate(mCancelView)
            mWindowManager = null
            mView = null
            mCancelView = null
        }
    }


    override fun onLayoutUpdate(x: Float, y: Float) {
        mWindowLayoutParams?.x = x.toInt()
        mWindowLayoutParams?.y = y.toInt()
        mWindowManager?.updateViewLayout(mView, mWindowLayoutParams)
    }

    override fun getLayoutParams(): WindowManager.LayoutParams? {
        return mWindowLayoutParams
    }

    //save sp x,y
    fun saveLayoutParams() {
        mWindowLayoutParams?.let {
            val xyString = "${it.x},${it.y}"
            var xAndy by TweakSharePreferenceUtil(SP_TWEAKS_FLOAT_WINDOW_LAYOUT_KEY, "")
            xAndy = xyString
        }
    }

}
