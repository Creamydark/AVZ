package com.creamydark.avz.domain.model

data class FirebaseAccountResponseData(
    val isLoading:Boolean = false,
    val isSuccessful : String = "",
    val error:String = ""
)
