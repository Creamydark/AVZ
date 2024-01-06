package com.creamydark.avz.enozItools

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class YenaTools {
    fun convertMillisToDateTime(
        milliseconds: Long,
        pattern: String= "MM/dd/yyyy HH:mm a"
    ): String {
        val instant = Instant.ofEpochMilli(milliseconds)
        val dateTime = instant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(formatter)
    }
    fun simpleDateFormatter(longTimestamp: Long ,pattern: String = "MMMM dd, yyyy hh:mm a"): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Date(longTimestamp)
        return dateFormat.format(date)
    }
    fun formatTimestamp(timestamp: Long, outputFormat: String = "MMM dd, yyyy"): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return dateFormat.format(date)
    }
    fun isNot24HoursAgo(timestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val twentyFourHoursInMillis = 24 * 60 * 60 * 1000 // 24 hours in milliseconds

        return currentTime - timestamp > twentyFourHoursInMillis
    }

    fun hoursAgo(timestamp: Long): Long {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timestamp
        return timeDifference / (60 * 60 * 1000)
    }

    fun isWithinThisWeek(timestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val oneWeekInMillis = 7 * 24 * 60 * 60 * 1000 // One week in milliseconds

        return currentTime - timestamp <= oneWeekInMillis
    }

    fun daysAgo(timestamp: Long): Long {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timestamp
        return timeDifference / (24 * 60 * 60 * 1000)
    }
    fun timestampToTimeString(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }
    fun finalTimestamp(timestamp:Long):String{
        return if (!isNot24HoursAgo(timestamp)) {
            val hoursAgo = hoursAgo(timestamp)
            "$hoursAgo Hours ago."
        } else {
            formatTimestamp(timestamp)
        }
    }
    fun finalTimestampv2(timestamp: Long):String{
        return if (isWithinThisWeek(timestamp)) {
            val hoursAgo = hoursAgo(timestamp)
            return when (val daysAgo = daysAgo(timestamp)) {
                0L -> "$hoursAgo Hours ago."
                1L -> "Yesterday at ${timestampToTimeString(timestamp)}"
                else -> "$daysAgo days ago at ${timestampToTimeString(timestamp)}"
            }
        } else {
            formatTimestamp(timestamp)
        }
    }
    fun timeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val difference = now - timestamp
        val seconds = TimeUnit.MILLISECONDS.toSeconds(difference)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(difference)
        val hours = TimeUnit.MILLISECONDS.toHours(difference)
        val days = TimeUnit.MILLISECONDS.toDays(difference)
        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            days < 7 -> "$days days ago"
            else -> {
                formatTimestamp(timestamp)
            }
        }
    }
}