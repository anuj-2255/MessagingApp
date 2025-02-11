package com.example.messagingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage

class SmsReceiver(private val smsReceiverCallBack:((SmsMessage?)->Unit)) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            messages.forEach { sms ->
                    smsReceiverCallBack.invoke(sms)
            }
        }
    }
}