package com.creamydark.avz.domain.model


data class UserData(
    val student: Boolean? = false,
    val email: String? = "",
    val uid : String? = "",
    val name : String? = "",
    val favoriteWords :List<String> = emptyList()
)
