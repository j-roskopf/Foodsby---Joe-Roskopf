package com.foodsby.main.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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

    @SuppressLint("InflateParams")
    fun createRadioButtonFromDropOff(dropoff: Dropoff, activity: Activity): RadioButton {
        var radioButton = activity.layoutInflater.inflate(R.layout.radio_button, null) as RadioButton
        radioButton = populateRadioButton(dropoff, radioButton, activity)
        return radioButton
    }

    /**
     * Given a [RadioButton], a [Dropoff], and an [Activity], populate the radio button with the correct parameters
     *
     * @param radioButton - the RadioButton to populate
     * @param dropOff - the Dropoff to get the day from
     * @param activity - the Activity to get resources
     */
    fun populateRadioButton(dropOff: Dropoff, radioButton: RadioButton, activity: Activity): RadioButton {
        val currentDay = currentDayFromDropOff(dropOff)

        if(currentDay == Calendar.getInstance().currentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))) {
            radioButton.text = activity.getString(R.string.today_text)
        } else {
            radioButton.text = abbreviationFromDay(currentDay)
        }
        radioButton.id = idFromDay(currentDay)
        return radioButton
    }

    /**
     * Given a [Dropoff] return the day of that dropoff, otherwise a question mark
     */
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
    fun abbreviationFromDay(day: String?): String {
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
