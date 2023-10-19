package com.creamydark.avz.domain.model

data class UserData(
    val email:String?="",
    val name: String? = "",
    val birthday: String? = "",
    val isStudent: Boolean? = false
)
