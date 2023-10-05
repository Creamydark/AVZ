package com.creamydark.avz.domain.model

data class ResponseWordScrollSnapShot(
    val items: List<ScrollableItem> = ArrayList(),
    val errorMessage:String
)
