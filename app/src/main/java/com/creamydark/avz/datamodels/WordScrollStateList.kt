package com.creamydark.avz.datamodels

data class ResponseWordScrollSnapShot(
    val items: List<ScrollableItem> = ArrayList(),
    val errorMessage:String
)
