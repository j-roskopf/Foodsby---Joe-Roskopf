package com.foodsby.model.network

import com.google.gson.annotations.SerializedName

data class Dropoff(
        @SerializedName("day")
        var day: String? = null,
        @SerializedName("deliveries")
        var deliveries: List<Delivery>? = null
)