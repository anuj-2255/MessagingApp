package com.example.messagingapp.view

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messagingapp.R
import com.example.messagingapp.databinding.ActivityMainBinding
import com.example.messagingapp.model.SmsData
import com.example.messagingapp.utils.MethodUtils.checkSmsPermission
import com.example.messagingapp.utils.SMS_PERMISSION_CODE
import com.example.messagingapp.receiver.SmsReceiver
import com.example.messagingapp.utils.showToast
import com.example.messagingapp.view.adapter.SmsAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var smsAdapter: SmsAdapter? = null
    private val smsList = mutableListOf<SmsData>()
    private val filteredSmsList = mutableListOf<SmsData>()
    private var searchByNumber: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    override fun onStart() {
        super.onStart()
        // Register SMS receiver
        registerReceiver(
            smsReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
        checkSmsPermission()
    }

    private fun initView() {
        binding.apply {
            rvSms.layoutManager = LinearLayoutManager(this@MainActivity)
            smsAdapter = SmsAdapter()
            rvSms.adapter = smsAdapter
            etNumber.clearFocus()
            etNumber.text.clear()
            etNumber.addTextChangedListener {
                searchByNumber = it?.toString()?.trim() ?: ""
                if (searchByNumber.isNullOrBlank()) {
                    smsAdapter?.setData(smsList)
                } else {
                    filteredSmsList.clear()
                    filteredSmsList.addAll(smsList.filter { smsData ->
                        smsData.sender?.contains(
                            searchByNumber ?: "", true
                        ) == true
                    })
                    smsAdapter?.setData(filteredSmsList)
                }
            }
        }
    }

    private val smsReceiver = SmsReceiver { smsMessage ->
        smsMessage?.let {
            newSmsCallBackReceived(it)
        } ?: run {
            showToast(getString(R.string.unable_to_detect_message))
        }
    }

    private fun checkSmsPermission() {
        val permissionList = arrayListOf<String>()
        permissionList.add(android.Manifest.permission.RECEIVE_SMS)
        permissionList.add(android.Manifest.permission.READ_SMS)
        checkSmsPermission(
            activity = this@MainActivity,
            permissionList = permissionList,
            permissionCode = SMS_PERMISSION_CODE,
            successCallBack = { loadSmsMessages() }
        )
    }

    private fun newSmsCallBackReceived(sms: SmsMessage) {
        val sender = sms.originatingAddress ?: ""
        smsList.add(
            0,
            SmsData(sender, sms.messageBody, System.currentTimeMillis())
        )
        if (searchByNumber.isNullOrEmpty() || sender == searchByNumber) {
            smsAdapter?.setData(smsList)
            binding.rvSms.smoothScrollToPosition(0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            loadSmsMessages()
        } else {
            showToast(R.string.sms_permissions_are_required_for_this_app)
        }
    }

    private fun loadSmsMessages() {
        smsList.clear()
        val cursor = contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE
            ),
            null,
            null,
            Telephony.Sms.DEFAULT_SORT_ORDER
        )

        cursor?.use {
            while (it.moveToNext()) {
                val sender = it.getString(0)
                val body = it.getString(1)
                val date = it.getLong(2)
                smsList.add(SmsData(sender, body, date))
            }
        }
        smsAdapter?.setData(smsList)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsReceiver)
    }
}
