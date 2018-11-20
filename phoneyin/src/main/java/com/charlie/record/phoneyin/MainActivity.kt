package com.charlie.record.phoneyin

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakViewDataType
import com.charlie.record.phoneyin.sample.ExampleTweakLibrary
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
                TweakViewDataType.integer,
                btn.width,
                0,
                screenWidth
            )
        )

        //button高度
        ExampleTweakLibrary.add(
            Tweak(
                "UI",
                "button",
                "height",
                TweakViewDataType.integer,
                btn.height,
                0,
                screenHeight
            )
        )


        TweakManager.with(this).initLibrary(ExampleTweakLibrary)

        btn.setOnClickListener {
            TweakManager.start()
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


        ExampleTweakLibrary.bind(ExampleTweakLibrary.switchButton1) {
            btn_example.visibility = if (it as Boolean) View.VISIBLE else View.INVISIBLE
            Log.d("visibility", it.toString())
        }

        val buttonTweaks = arrayListOf<Tweak>(
            ExampleTweakLibrary.getTweakFromKey("UI_button_width")!!,
            ExampleTweakLibrary.getTweakFromKey("UI_button_height")!!
        )

        ExampleTweakLibrary.bindMultiple(buttonTweaks) {
            val width = ExampleTweakLibrary.getTweakValue("UI_button_width")
            val height = ExampleTweakLibrary.getTweakValue("UI_button_height")

            val layout = btn_example.layoutParams
            layout.width = width as Int
            layout.height = height as Int
            btn_example.layoutParams = layout
        }
    }
}
