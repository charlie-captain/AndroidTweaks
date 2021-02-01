package com.charlie.example.sample

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.Tweak
import com.charlie.androidtweaks.data.TweakFloat
import com.charlie.example.BaseActivity
import com.charlie.example.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {
        btn.setOnClickListener {
            TweakManager.startActivity()
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

//        val layout = btn_example.layoutParams
//        layout.width = width.toInt()
//        layout.height = height.toInt()
//        btn_example.layoutParams = layout

        text.text = ExampleTweakLibrary.stringtext.value

    }
}
