package com.charlie.record.phoneyin.sample

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakInt
import com.charlie.record.phoneyin.BaseActivity
import com.charlie.record.phoneyin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        //动态添加,下面举个例子
        ExampleTweakLibrary.add(
            Tweak(
                "UI",
                "button",
                "width",
                TweakInt(
                    btn.width,
                    0,
                    screenWidth
                )
            )
        )

        //button高度
        ExampleTweakLibrary.add(
            Tweak(
                "UI",
                "button",
                "height",
                TweakInt(
                    btn.height,
                    0,
                    screenHeight
                )
            )
        )

        btn.setOnClickListener {
            TweakManager.setFloatWindow(true).initLibrary(ExampleTweakLibrary).start()
        }
    }

    override fun setLayout() = R.layout.activity_main


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        var transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()


        btn_example.visibility = if (ExampleTweakLibrary.switchButton1.value as Boolean) View.VISIBLE else View.INVISIBLE


        val width = ExampleTweakLibrary.value("UI_button_width")
        val height = ExampleTweakLibrary.value("UI_button_height")

        val layout = btn_example.layoutParams
        layout.width = width as Int
        layout.height = height as Int
        btn_example.layoutParams = layout

        text.text = ExampleTweakLibrary.stringtext.value as CharSequence?


    }
}
