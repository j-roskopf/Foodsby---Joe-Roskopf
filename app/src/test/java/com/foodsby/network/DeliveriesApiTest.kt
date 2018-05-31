package com.foodsby.network

import android.content.Context
import junit.framework.Assert.fail
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeliveriesApiTest {

    @Test
    fun getDeliveriesTest() {
        /**
         * Verify we have 7 days worth of dropoffs in our sample JSON
         */
        val stream = javaClass.getResourceAsStream("deliveries_sample.json")
        if(stream == null) {
            fail("stream is null")
        }

        val applicationContext = mock(Context::class.java)
        val deliveriesApi = DeliveriesApi(applicationContext)

        val response = deliveriesApi.getDeliveries(stream)

        assertEquals(response.dropoffs?.size, 7)
    }
}