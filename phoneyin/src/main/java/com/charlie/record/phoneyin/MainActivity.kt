package com.charlie.record.phoneyin

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init() {
        tv.text= DateUtil.gets()


    }

    override fun setLayout() = R.layout.activity_main



}
