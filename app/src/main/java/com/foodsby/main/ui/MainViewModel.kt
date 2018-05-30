package com.foodsby.main.ui

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.widget.RadioButton
import com.foodsby.R
import com.foodsby.android.extensions.currentDay
import com.foodsby.model.network.DeliveryResponse
import com.foodsby.model.network.Dropoff
import com.foodsby.network.DeliveriesApi
import kotlinx.coroutines.experimental.async
import java.util.*
import javax.inject.Inject


class MainViewModel @Inject constructor(private val deliveriesApi: DeliveriesApi) : ViewModel() {

    /**
     * Our [DeliveryResponse] to be observed in the Fragment / Activity
     */
    var deliveryResponse: MutableLiveData<DeliveryResponse> = MutableLiveData()

    /**
     * Launches an async coroutine to fetch the deliveries!
     */
    fun fetchDeliveries() {
        async {
            deliveryResponse.postValue(deliveriesApi.getDeliveries())
        }
    }

    fun createRadioButtonFromDropOff(dropoff: Dropoff, activity: Activity): RadioButton {
        val radioButton = activity.layoutInflater.inflate(R.layout.radio_button, null) as RadioButton

        val currentDay = currentDayFromDropOff(dropoff)

        if(currentDay == Calendar.getInstance().currentDay()) {
            radioButton.text = "Today"
        } else {
            radioButton.text = abbreviationFromDay(currentDay)
        }
        radioButton.id = idFromDay(currentDay)
        Log.d("D","responseDebug - setting id of day ${radioButton.text} ${radioButton.id}")
        return radioButton
    }

    fun currentDayFromDropOff(dropoff: Dropoff): String {
        return dropoff.day ?: "?"
    }



    /**
     * Returns the abbreviation for a day based on the day passed in
     *
     * @param day - the day to abbreviate
     *
     * @return the abbreviated day
     */
    private fun abbreviationFromDay(day: String?): String {
        return when(day?.toUpperCase()) {
            "MONDAY" -> "Mon"
            "TUESDAY" -> "Tues"
            "WEDNESDAY" -> "Wed"
            "THURSDAY" -> "Thur"
            "FRIDAY" -> "Fri"
            "SATURDAY" -> "Sat"
            "SUNDAY" -> "Sun"
            else -> "?"
        }
    }

    /**
     * Returns an arbitrarily chosen ID for a day based on the day passed in
     *
     * @param day - the day to abbreviate
     *
     * @return the ID to be used
     */
    fun idFromDay(day: String?): Int {
        return when(day?.toUpperCase()) {
            "MONDAY" -> 10
            "TUESDAY" -> 11
            "WEDNESDAY" -> 12
            "THURSDAY" -> 13
            "FRIDAY" -> 14
            "SATURDAY" -> 15
            "SUNDAY" -> 16
            else -> -1
        }
    }
}
