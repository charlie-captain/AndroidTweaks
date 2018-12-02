package com.charlie.androidtweaks.window

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.utils.TweakPermissionUtil
import com.charlie.androidtweaks.utils.TweakUtil

object TweakWindowManager : TweakWindowImp, TweakFloatView.OnViewLayoutParamsListener {
    private var mWindowManager: WindowManager? = null

    private var mWindowLayoutParams: WindowManager.LayoutParams? = null


    private var mView: TweakFloatView? = null

    private var mCancelView: TweakFloatCancelView? = null

    private var mWidth: Int = 0

    override fun showPermission(context: Context) {
        showWindow(context)
    }


    override fun showWindow(context: Context) {
        if (mWindowManager == null && mView == null && mCancelView == null) {
            mWidth = TweakUtil.dimen(R.dimen.tweaks_width_float_window, context.resources)
            mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            mView = TweakFloatView(context)
            mView?.setSize(mWidth)
            mView?.setWindowLayoutParams(this)
            mCancelView = TweakFloatCancelView(context)

            mWindowLayoutParams = WindowManager.LayoutParams()
            mWindowLayoutParams?.type = WindowManager.LayoutParams.TYPE_PHONE
            mWindowLayoutParams?.format = PixelFormat.RGBA_8888
            mWindowLayoutParams?.gravity = Gravity.START or Gravity.TOP
            mWindowLayoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            mWindowLayoutParams?.width = TweakUtil.dp2px(mWidth.toFloat(), context.resources)
            mWindowLayoutParams?.height = TweakUtil.dp2px(mWidth.toFloat(), context.resources)

            mWindowManager?.addView(mView, mWindowLayoutParams)

        }
    }


    override fun dismissWindow() {
        if (mWindowManager != null && mView != null) {
            mWindowManager?.removeViewImmediate(mView)
//            mWindowManager?.removeViewImmediate(mCancelView)
            mWindowManager = null
            mView = null
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

}
