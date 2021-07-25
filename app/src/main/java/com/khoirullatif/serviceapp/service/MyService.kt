package com.khoirullatif.serviceapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() { // Kelas service berjalan di ui thread

    companion object {
        internal val TAG = MyService::class.java.simpleName
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service dijalankan...")
        serviceScope.launch { // Simulasi untuk menjalankan proses yang sulit, shg membuat thread baru di background
            delay(3000)
            stopSelf() // untuk mematikan MyService dari sistem android
            Log.d(TAG, "Service dimatikan..")
        }
        return START_STICKY //service akan dimatikan jika kekurangan memori, diciptakan kembali jika sudah ada memori
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy:...")
    }
}