package com.khoirullatif.serviceapp.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1000
        internal const val  EXTRA_DURATION = "extra_duration"
        internal val TAG = MyJobIntentService::class.java.simpleName

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Dimulai..")
        val duration = intent.getLongExtra(EXTRA_DURATION, 0)
        try {
            for (i in 0..duration) {
                if (i % 200 == 0L) {
                    Thread.sleep(1000)
                    Log.d(TAG, "proses: $i")
                }
            }
            Log.d(TAG, "onHandleWork: Selesai..")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}