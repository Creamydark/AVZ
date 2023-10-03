package com.creamydark.avz.datamodels

data class FirebaseAccountResponseData(
    val isLoading:Boolean = false,
    val isSuccessful : String = "",
    val error:String = ""
)
