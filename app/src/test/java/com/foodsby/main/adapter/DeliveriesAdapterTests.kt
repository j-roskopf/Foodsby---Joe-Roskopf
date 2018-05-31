package com.foodsby.main.adapter

import android.content.Context
import com.foodsby.R
import com.foodsby.model.network.Delivery
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeliveriesAdapterTests {

    lateinit var delivery: Delivery
    lateinit var context: Context
    lateinit var adapter: DeliveriesAdapter

    @Before
    fun setup() {
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

        context = mock(Context::class.java)
        adapter = DeliveriesAdapter(context = context, deliveries = listOf())

    }

    @Test
    fun getBackgroundColorFromDropOffTest() {
        /**
         * We want to test to make sure the UI reacts appropriately for deliveries with appropriate status
         */

        assertEquals(adapter.getBackgroundColorFromDelivery(delivery), android.R.color.white)

        val soldOutDelivery = delivery.copy(soldOut = true)
        assertEquals(adapter.getBackgroundColorFromDelivery(soldOutDelivery), R.color.material_red_500)

        val sellingOutDelivery = delivery.copy(sellingOut = true)
        assertEquals(adapter.getBackgroundColorFromDelivery(sellingOutDelivery), R.color.material_yellow_500)

        val isPastCutOffDelivery = delivery.copy(isPastCutoff = true)
        assertEquals(adapter.getBackgroundColorFromDelivery(isPastCutOffDelivery), R.color.material_red_500)

        val orderPlacedDelivery = delivery.copy(isOrderPlaced = true)
        assertEquals(adapter.getBackgroundColorFromDelivery(orderPlacedDelivery), R.color.material_green_500)
    }

    @Test
    fun getStatusFromDeliveryTest() {
        /**
         * We want to test to make sure the status text updates accordingly based on our delivery
         */

        assertEquals(adapter.getStatusFromDelivery(delivery), -1)

        val soldOutDelivery = delivery.copy(soldOut = true)
        assertEquals(adapter.getStatusFromDelivery(soldOutDelivery), R.string.delivery_item_sold_out)

        val sellingOutDelivery = delivery.copy(sellingOut = true)
        assertEquals(adapter.getStatusFromDelivery(sellingOutDelivery), R.string.delivery_item_selling_out)

        val isPastCutOffDelivery = delivery.copy(isPastCutoff = true)
        assertEquals(adapter.getStatusFromDelivery(isPastCutOffDelivery), R.string.delivery_item_past_cut_off)

        val orderPlacedDelivery = delivery.copy(isOrderPlaced = true)
        assertEquals(adapter.getStatusFromDelivery(orderPlacedDelivery), R.string.delivery_item_order_placed)
    }

    @Test
    fun canBeExpandedTest() {
        /**
         * Not all of our items can be expanded. Make sure that based on delivery, the UI knows whether or not it can be expanded
         */

        assertEquals(adapter.canBeExpanded(delivery), false)

        val soldOutDelivery = delivery.copy(soldOut = true)
        assertEquals(adapter.canBeExpanded(soldOutDelivery), true)

        val sellingOutDelivery = delivery.copy(sellingOut = true)
        assertEquals(adapter.canBeExpanded(sellingOutDelivery), true)

        val isPastCutOffDelivery = delivery.copy(isPastCutoff = true)
        assertEquals(adapter.canBeExpanded(isPastCutOffDelivery), true)

        val orderPlacedDelivery = delivery.copy(isOrderPlaced = true)
        assertEquals(adapter.canBeExpanded(orderPlacedDelivery), true)
    }

    @Test
    fun getImageFromDeliveryTest() {
        /**
         * We want to make sure the images are set correctly based on the delivery status
         */
        assertEquals(adapter.getImageFromDelivery(delivery), android.R.drawable.screen_background_dark_transparent)

        val soldOutDelivery = delivery.copy(soldOut = true)
        assertEquals(adapter.getImageFromDelivery(soldOutDelivery), R.drawable.ic_sentiment_dissatisfied_red_500_24dp)

        val sellingOutDelivery = delivery.copy(sellingOut = true)
        assertEquals(adapter.getImageFromDelivery(sellingOutDelivery), R.drawable.ic_warning_yellow_500_24dp)

        val isPastCutOffDelivery = delivery.copy(isPastCutoff = true)
        assertEquals(adapter.getImageFromDelivery(isPastCutOffDelivery), R.drawable.ic_timer_off_red_500_24dp)

        val orderPlacedDelivery = delivery.copy(isOrderPlaced = true)
        assertEquals(adapter.getImageFromDelivery(orderPlacedDelivery), R.drawable.ic_check_circle_green_500_24dp)
    }

}