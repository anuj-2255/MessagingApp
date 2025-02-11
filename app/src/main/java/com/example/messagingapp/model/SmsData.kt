package com.example.messagingapp.model

import com.example.messagingapp.utils.SMS_DATE_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SmsData (
    val sender: String?,
    val body: String?,
    val timestamp: Long?
){
    fun formattedDate() = timestamp?.let { SimpleDateFormat(SMS_DATE_TIME_FORMAT,
            Locale.getDefault()
        ).format(Date(it))
    }?:run{""}
}