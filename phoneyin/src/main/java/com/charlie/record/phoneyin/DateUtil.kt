package com.charlie.record.phoneyin

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtil{

    fun Date.dateFormat() : String{
        return SimpleDateFormat("yyyy-MM-dd").format(this)
    }


    fun gets():String{
        Log.d("ss",Date().dateFormat())
        return Date().dateFormat()
    }
}