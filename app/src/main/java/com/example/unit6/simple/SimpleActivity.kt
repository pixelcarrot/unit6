package com.example.unit6.simple

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.unit6.databinding.ActivitySimpleBinding
import com.example.unit6.simple.worker.LogWorker
import java.util.concurrent.TimeUnit

class SimpleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doWork()
    }

    private fun doWork() {
        val workRequest = OneTimeWorkRequestBuilder<LogWorker>()
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this)
            .enqueue(workRequest)
    }

}
