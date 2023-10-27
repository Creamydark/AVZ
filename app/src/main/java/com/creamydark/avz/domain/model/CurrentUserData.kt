package com.creamydark.avz.domain.model

import android.net.Uri


data class CurrentUserData(
    val displayName:String?="",
    val email: String? = "",
    val uid :String? = "",
    val photoUri: Uri? = null
)
