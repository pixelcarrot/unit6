package com.example.unit6.complex.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.unit6.complex.api.NoteApi
import com.example.unit6.complex.repository.NoteRepository
import com.example.unit6.complex.repository.NoteRepositoryImpl

class CallAPIWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val repository: NoteRepository = NoteRepositoryImpl(NoteApi.retrofitService)

    override suspend fun doWork(): Result {
        try {
            repository.submitNote("Random Message from App: ${System.currentTimeMillis()}")
            return Result.success()
        } catch (ex: Exception) {
            return Result.retry()
        }
    }
}