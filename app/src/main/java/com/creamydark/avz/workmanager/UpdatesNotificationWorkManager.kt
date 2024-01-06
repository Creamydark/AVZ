package com.creamydark.avz.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@HiltWorker
class UpdatesNotificationWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted parameters: WorkerParameters,
    private val repository: UpdatesFirestoreRepository
): CoroutineWorker(appContext, parameters) {
    override suspend fun doWork(): Result {
        val currentDate = LocalDate.now()
//        val currentDateAsDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        Log.d("UpdatesNotificationWorkManager", "doWork: Work na please")
        repository.getAllDocumentsForWorkManager().collectLatest {
            list ->
            list.forEach {
                data->
                Log.d("UpdatesNotificationWorkManager", "getAllDocuments: ${data.id}")
            }
        }
        return Result.success()
    }

}