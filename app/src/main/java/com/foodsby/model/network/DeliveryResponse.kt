package com.foodsby.model.network

import com.google.gson.annotations.SerializedName


data class DeliveryResponse(
        @SerializedName("dropoffs")
        var dropoffs: List<Dropoff>? = null
)