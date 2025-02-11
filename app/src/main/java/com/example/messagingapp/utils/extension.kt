package com.example.messagingapp.utils

import android.app.Activity
import android.widget.Toast

var toast: Toast? = null
fun Activity.showToast(text: Any) {
    toast?.cancel()
    when (text) {
        is Int -> {
            toast =
                Toast.makeText(this.applicationContext, this.getString(text), Toast.LENGTH_SHORT)
        }
        is String -> {
            toast = Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT)
        }
    }
    toast?.show()
}