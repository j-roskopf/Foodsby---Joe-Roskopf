package com.foodsby.main.ui

import android.app.Activity
import android.widget.RadioButton
import com.foodsby.android.extensions.currentDay
import com.foodsby.model.network.Delivery
import com.foodsby.model.network.Dropoff
import com.foodsby.network.DeliveriesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTests {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var delivery: Delivery
    private lateinit var dropOff: Dropoff


    @Before
    fun setup() {
        val deliveriesApi = mock(DeliveriesApi::class.java)
        mainViewModel = MainViewModel(deliveriesApi)
        delivery = Delivery(
                deliveryId = 1,
                storeId = 2,
                restaurantName = "Test Name",
                logoUrl = "https://logo.url",
                cutoff = "Now!",
                dropoff = "Now!",
                soldOut = false,
                sellingOut = false,
                isOrderPlaced = false,
                isPastCutoff = false
        )

        dropOff = Dropoff(day = "Thursday", deliveries = listOf(delivery))
    }

    @Test
    fun idFromDayTest() {
        /**
         * Given a day string, return an arbitrary ID
         */

        assertEquals(mainViewModel.idFromDay("monday"), 10)
        assertEquals(mainViewModel.idFromDay("tuesday"), 11)
        assertEquals(mainViewModel.idFromDay("wednesday"), 12)
        assertEquals(mainViewModel.idFromDay("thursday"), 13)
        assertEquals(mainViewModel.idFromDay("friday"), 14)
        assertEquals(mainViewModel.idFromDay("saturday"), 15)
        assertEquals(mainViewModel.idFromDay("sunday"), 16)
        assertEquals(mainViewModel.idFromDay("asdf"), -1)
    }

    @Test
    fun abbreviationFromDayTest() {
        /**
         * Given a day string, make sure we get the right abbreviation back
         */
        assertEquals(mainViewModel.abbreviationFromDay("monday"), "Mon")
        assertEquals(mainViewModel.abbreviationFromDay("tuesday"), "Tues")
        assertEquals(mainViewModel.abbreviationFromDay("wednesday"), "Wed")
        assertEquals(mainViewModel.abbreviationFromDay("thursday"), "Thur")
        assertEquals(mainViewModel.abbreviationFromDay("friday"), "Fri")
        assertEquals(mainViewModel.abbreviationFromDay("saturday"), "Sat")
        assertEquals(mainViewModel.abbreviationFromDay("sunday"), "Sun")
        assertEquals(mainViewModel.abbreviationFromDay("asdf"), "?")
    }

    @Test
    fun currentDayFromDropOffTest() {
        assertEquals(mainViewModel.currentDayFromDropOff(dropOff), "Monday")

        val nullDayDropOff = dropOff.copy(day = null)
        assertEquals(mainViewModel.currentDayFromDropOff(nullDayDropOff), "?")
    }

    @Test
    fun populateRadioButtonTests() {
        val activity = mock(Activity::class.java)
        val mockedRadioButton = mock(RadioButton::class.java)
        val radioButton = mainViewModel.populateRadioButton(activity = activity, dropOff = dropOff, radioButton = mockedRadioButton)

        //verify the id of the radio button
        Mockito.`when`(radioButton.id).thenReturn(mainViewModel.idFromDay(dropOff.day))
        assertEquals(radioButton.id, mainViewModel.idFromDay(dropOff.day))

        val currentDay = mainViewModel.currentDayFromDropOff(dropOff)

        if(currentDay == Calendar.getInstance().currentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))) {
            //verify the text of the radio button is today
            Mockito.`when`(radioButton.text).thenReturn("Today")
            assertEquals(radioButton.text, "Today")
        } else {
            Mockito.`when`(radioButton.text).thenReturn(mainViewModel.abbreviationFromDay(currentDay))
            //verify the text of the radio button is the day of the week abbreviated
            assertEquals(radioButton.text, mainViewModel.abbreviationFromDay(currentDay))
        }
    }
}