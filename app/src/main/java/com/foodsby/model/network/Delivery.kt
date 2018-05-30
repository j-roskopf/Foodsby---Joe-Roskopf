package com.foodsby.model.network

import com.google.gson.annotations.SerializedName

data class Delivery(
        @SerializedName("deliveryId")
        var deliveryId: Int? = null,
        @SerializedName("storeId")
        var storeId: Int? = null,
        @SerializedName("restaurantName")
        var restaurantName: String? = null,
        @SerializedName("logoUrl")
        var logoUrl: String? = null,
        @SerializedName("cutoff")
        var cutoff: String? = null,
        @SerializedName("dropoff")
        var dropoff: String? = null,
        @SerializedName("soldOut")
        var soldOut: Boolean? = null,
        @SerializedName("sellingOut")
        var sellingOut: Boolean? = null,
        @SerializedName("isPastCutoff")
        var isPastCutoff: Boolean? = null,
        @SerializedName("isOrderPlaced")
        var isOrderPlaced: Boolean? = null
)