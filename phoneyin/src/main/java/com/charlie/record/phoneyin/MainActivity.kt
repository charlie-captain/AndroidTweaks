package com.charlie.record.phoneyin

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.charlie.androidtweaks.Tweak
import com.charlie.androidtweaks.TweakManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {
        btn.setOnClickListener {
            //            startActivity(Intent(this, TweakActivity::class.java))

            var list: MutableList<Tweak> = arrayListOf()



            TweakManager.with(this).addAll(list).start()
        }
    }

    override fun setLayout() = R.layout.activity_main


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        var transaction = beginTransaction()
        transaction.func()
        transaction.commit()
    }

}
