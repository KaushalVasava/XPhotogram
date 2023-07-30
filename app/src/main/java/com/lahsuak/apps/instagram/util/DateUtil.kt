package com.lahsuak.apps.instagram.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun getDateTime(timeInMillis: Long) : String{
        val sdf = SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }
}