package com.charlie.record.phoneyin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        init()
    }


    protected abstract fun init()

    protected abstract fun setLayout():Int

}