package com.creamydark.avz.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UpdatesPostData(
    val emailUploader: String="",
    val displayName: String="",
    val caption: String="",
    @ServerTimestamp val timestamp: Date?=Date(),
    val profilePhoto: String="",
    val imagePostLink: String="",
    val id:String=""
)
