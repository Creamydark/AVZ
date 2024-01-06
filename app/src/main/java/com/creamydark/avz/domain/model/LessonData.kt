package com.creamydark.avz.domain.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class LessonData(
    val title:String = "",
    val subtitle:String = "",
    val id:String = "",
    @ServerTimestamp @PropertyName("timestamp") val timestamp: Date?= Date(),
    val description:String = "",
    val who_posted_email:String="",
    val who_posted_profilePhoto: String="",
    val who_posted_name: String="",
    val pdf_link:String = ""
    )