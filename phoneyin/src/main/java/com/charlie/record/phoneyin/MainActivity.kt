package com.charlie.record.phoneyin

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.Tweak
import com.charlie.androidtweaks.TweakManager
import com.charlie.androidtweaks.TweakViewDataType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {
        val screenWidth = resources.displayMetrics.widthPixels
        //动态添加
        ExampleTweakLibrary.add(Tweak("UI", "button", "width", TweakViewDataType.integer, btn.width, 0, screenWidth))

        TweakManager.with(this).addAll(ExampleTweakLibrary.tweakStore.tweaks)

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

        ExampleTweakLibrary.bind(ExampleTweakLibrary.getTweakbyKey("UI_button_width")) {
            var layout = btn_example.layoutParams
            layout.width = it as Int
            btn_example.layoutParams = layout
        }
    }
}
