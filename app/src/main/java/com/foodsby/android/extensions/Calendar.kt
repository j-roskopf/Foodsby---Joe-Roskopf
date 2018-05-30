package com.foodsby.android.extensions

import java.util.*

fun Calendar?.currentDay(): String {
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_WEEK)
    return when (day) {
        Calendar.SUNDAY -> "Sunday"
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        else -> "?"
    }
}