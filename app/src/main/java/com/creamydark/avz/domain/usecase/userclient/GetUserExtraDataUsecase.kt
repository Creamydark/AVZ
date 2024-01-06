package com.creamydark.avz.domain.usecase.userclient

import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetUserExtraDataUsecase @Inject constructor() {
    suspend fun execute(email:String):Flow<ResultType<UserData?>>{
        return callbackFlow {  }
    }
}