package com.example.unit6.simple.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LogWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("SimpleTask", "Hello world $runAttemptCount ${System.currentTimeMillis()}")
        return Result.success()
    }

}