package com.example.ttss.util

class DateHandler {
    companion object{
        fun getDateLong(day:Int):Long{
            return System.currentTimeMillis() - 86400000 * day
        }
    }
}