package com.creamydark.avz.domain.usecase.postupdates

import android.net.Uri
import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostNewUpdateUseCase @Inject constructor(private val repository: UpdatesFirestoreRepository) {
    suspend fun invoke(data: UpdatesPostData,postImg:Uri?):Flow<ResultType<String>>{
        return repository.post(data = data, postImage = postImg)
    }
}