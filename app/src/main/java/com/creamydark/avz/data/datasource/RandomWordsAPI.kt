package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.model.SomeRandomWordData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RandomWordsAPI {
    @GET("/v1/randomword")
    fun generateRandomWords(
        @Header("X-Api-Key")apikey:String
    ):Call<SomeRandomWordData>
}

