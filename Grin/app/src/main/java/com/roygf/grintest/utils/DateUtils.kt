package com.roygf.grintest.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils{

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getDate(dateText : String) : Date {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'")
            return formatter.parse(dateText)
        }

        @SuppressLint("SimpleDateFormat")
        fun formatDate(date : Date?) : String {
            val formatter = SimpleDateFormat("MMMM-dd-yyyy")
            return formatter.format(date)
        }
    }
}