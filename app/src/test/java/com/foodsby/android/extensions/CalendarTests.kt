package com.foodsby.android.extensions

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CalendarTests {

    @Test
    fun currentDayTest() {
        /**
         * Test to make sure that we get the correct string from our current day method
         */

        val calendar = Calendar.getInstance()

        assertEquals("Sunday", calendar.currentDay(Calendar.SUNDAY))
        assertEquals("Monday", calendar.currentDay(Calendar.MONDAY))
        assertEquals("Tuesday", calendar.currentDay(Calendar.TUESDAY))
        assertEquals("Wednesday", calendar.currentDay(Calendar.WEDNESDAY))
        assertEquals("Thursday", calendar.currentDay(Calendar.THURSDAY))
        assertEquals("Friday", calendar.currentDay(Calendar.FRIDAY))
        assertEquals("Saturday", calendar.currentDay(Calendar.SATURDAY))
        assertEquals("?", calendar.currentDay(-1))

    }
}