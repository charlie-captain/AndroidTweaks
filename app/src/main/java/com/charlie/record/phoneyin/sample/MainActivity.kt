package com.charlie.record.phoneyin.sample

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakFloat
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
                TweakFloat(
                    100f,
                    max = screenWidth.toFloat()
                )
            )
        )

        //button高度
        ExampleTweakLibrary.add(
            Tweak(
                "UI",
                "button",
                "height",
                TweakFloat(
                    100f,
                    max = screenHeight.toFloat()
                )
            )
        )

        btn.setOnClickListener {
            TweakManager.start(ExampleTweakLibrary)
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

        Log.d("aaa", "${ExampleTweakLibrary.switchButton1.value}")

        btn_example.visibility =
            if (ExampleTweakLibrary.switchButton1.value) View.VISIBLE else View.INVISIBLE


        val width: Float = ExampleTweakLibrary.value("UI_button_width", 200f)
        val height: Float = ExampleTweakLibrary.value("UI_button_height", 200f)

        val layout = btn_example.layoutParams
        layout.width = width.toInt()
        layout.height = height.toInt()
        btn_example.layoutParams = layout

        text.text = ExampleTweakLibrary.stringtext.value

    }
}
