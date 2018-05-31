package com.foodsby.network

import android.content.Context
import com.foodsby.R
import com.foodsby.model.network.DeliveryResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter
import javax.inject.Inject


class DeliveriesApi @Inject constructor(private val applicationContext: Context){

    /**
     * Return an [InputStream] of our sample JSON
     *
     * @return InputStream of our sample JSON
     */
    fun getInputStream(): InputStream {
        return applicationContext.resources.openRawResource(R.raw.deliveries_sample)
    }

    /**
     * Get the deliveries! Right now we are only getting the deliveries from the sample JSON file,
     * but once an API becomes available, this method would ideally retrieve the response from the API
     *
     * @return - [DeliveryResponse] - A response object representing our response from the API / sample JSON file
     */
    fun getDeliveries(inputStream: InputStream): DeliveryResponse {
        val writer = StringWriter()
        val buffer = CharArray(1024)
        inputStream.use {
            val reader = BufferedReader(InputStreamReader(it, "UTF-8"))
            var n: Int = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        }

        val gson = Gson()
        return gson.fromJson(writer.toString(), DeliveryResponse::class.java)
    }
}