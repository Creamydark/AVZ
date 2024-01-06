package com.creamydark.avz.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class WordsDataModel(
    val title:String="",
    val description:String="",
    val example:String="",
    @ServerTimestamp val timestamp:Date?=Date(),
    val uploader:String = "",
    val id:String = ""
)
