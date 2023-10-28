package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.AnnouncementRepository
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAnnouncementsUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend fun invoke():Flow<ResultType<List<AnnouncementPostData>>> =repository.getAllAnnouncements()
}