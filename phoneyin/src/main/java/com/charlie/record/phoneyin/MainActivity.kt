package com.charlie.record.phoneyin

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.TweakActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {

//        supportFragmentManager.inTransaction {
//            replace(R.id.fl_content,TweakFragment())
//        }

        btn.setOnClickListener {
            startActivity(Intent(this, TweakActivity::class.java))
        }
    }

    override fun setLayout() = R.layout.activity_main


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        var transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

}
