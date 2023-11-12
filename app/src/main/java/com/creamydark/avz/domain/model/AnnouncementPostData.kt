package com.creamydark.avz.domain.model

data class AnnouncementPostData(
    val emailUploader: String="",
    val displayName: String="",
    val caption: String="",
    val timestamp: Long=0,
    val likesCount: Int=0,
    val commentsCount: Int=0,
    val profilePhoto: String="",
    val imagePost: String=""
)
