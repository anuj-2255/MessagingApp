package com.example.messagingapp.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object MethodUtils {

    fun checkSmsPermission(activity: Activity,permissionList:List<String>, permissionCode:Int, successCallBack:(()->Unit)) {

        var permissionGranted = true

        for(permission in permissionList) {
            if(ContextCompat.checkSelfPermission(activity,permission)!=PackageManager.PERMISSION_GRANTED){
                permissionGranted = false
                break
            }
        }

        if(permissionGranted){
            successCallBack.invoke()
        }else{
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.READ_SMS
                ),
                permissionCode
            )
        }
    }
}