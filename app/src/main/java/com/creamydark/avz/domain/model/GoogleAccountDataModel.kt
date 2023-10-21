package com.creamydark.avz.domain.model

import android.net.Uri

data class GoogleAccountDataModel(
    val personName :String?="",
    val personGivenName :String?="",
    val personFamilyName :String?="",
    val personEmail :String?="",
    val personId:String?="",
    val personPhoto :Uri?=null
)
