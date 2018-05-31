package com.foodsby.android.extensions

import java.util.*

/**
 * Given a day of the week, return the string that represents that day
 *
 * @param dayOfWeek - the day of the week
 *
 * @return the English representation of that day of the week
 */
fun Calendar.currentDay(dayOfWeek: Int): String {
    return when (dayOfWeek) {
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