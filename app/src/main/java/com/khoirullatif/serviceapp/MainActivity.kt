package com.khoirullatif.serviceapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.khoirullatif.serviceapp.databinding.ActivityMainBinding
import com.khoirullatif.serviceapp.service.MyBoundService
import com.khoirullatif.serviceapp.service.MyJobIntentService
import com.khoirullatif.serviceapp.service.MyService

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    /////// Service connection, untuk menghubungkan MainActivity dengan MyBoundService
    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mServiceBound = false
        }

    }
    ////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener(this)
        binding.btnStartJobIntentService.setOnClickListener(this)
        binding.btnStartStartBoundService.setOnClickListener(this)
        binding.btnStopBoundService.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_start_service -> {
                val mIntentService = Intent(this, MyService::class.java)
                startService(mIntentService)
            }
            R.id.btn_start_job_intent_service -> {
                val mIntentJobIntentService = Intent(this, MyJobIntentService::class.java)
                mIntentJobIntentService.putExtra(MyJobIntentService.EXTRA_DURATION, 4000L)
                MyJobIntentService.enqueueWork(this, mIntentJobIntentService)
            }
            R.id.btn_start_start_bound_service -> {
                val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
                bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
            }
            R.id.btn_stop_bound_service -> {
                unbindService(mServiceConnection)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}