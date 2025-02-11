package com.example.messagingapp

import android.app.Application

class MessagingApp : Application() {

    companion object {
        lateinit var instance: MessagingApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}