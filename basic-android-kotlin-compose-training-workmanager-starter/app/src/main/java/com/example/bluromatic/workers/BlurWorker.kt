package com.example.bluromatic.workers

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.content.Context
import com.example.bluromatic.R

class BlurWorker(ctx: Context, params: WorkerParameters)
    : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        makeStatusNotification(
            applicationContext.resources.getString(R.string.blurring_image),
            applicationContext
        )
        return try {
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}