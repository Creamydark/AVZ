package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.SomeRandomWordData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RandomWordsRepository @Inject constructor(private val api:RandomWordsAPI?) {
    suspend fun requestRandomWords():Flow<ResultType<String?>>{
        return callbackFlow {
            try {
                val call = api?.generateRandomWords("hal9x8jRbkqlDGjvatgaBQ==q4qM0D22fRSh8Nyu")
                call?.enqueue(
                    object : Callback<SomeRandomWordData?> {
                        override fun onResponse(
                            call: Call<SomeRandomWordData?>,
                            response: Response<SomeRandomWordData?>
                        ) {
                            trySend(ResultType.success(data = response.body()?.word))
                            close()
                        }
                        override fun onFailure(call: Call<SomeRandomWordData?>, t: Throwable) {
                            trySend(ResultType.error(t))
                            close()
                        }
                    },
                )
            }catch (e:Throwable){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose()
        }
    }

}