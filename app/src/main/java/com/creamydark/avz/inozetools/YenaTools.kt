package com.creamydark.avz.inozetools

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class YenaTools {
    fun convertMillisToDateTime(
        milliseconds: Long,
        pattern: String= "MM-dd-yyyy HH:mm a"
    ): String {
        val instant = Instant.ofEpochMilli(milliseconds)
        val dateTime = instant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(formatter)
    }
}