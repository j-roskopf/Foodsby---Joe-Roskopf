package com.foodsby.main.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.foodsby.R
import com.foodsby.model.network.Delivery
import kotlinx.android.synthetic.main.delivery_row_item.view.*



class DeliveriesAdapter(private var deliveries: List<Delivery>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.delivery_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dropoff = deliveries[position]

        holder.deliveryItemRestaurantName.text = dropoff.restaurantName
        holder.deliveryItemOrderByTime.text = dropoff.cutoff
        holder.deliveryItemDeliveryTime.text = dropoff.dropoff
        holder.deliveryItemRestaurantImage.setImageURI(Uri.parse(dropoff.logoUrl))

        when {
            dropoff.isPastCutoff == true -> holder.deliveryItemOrderStatus.text = "Cut-Off Time Passed"
            dropoff.isOrderPlaced == true -> holder.deliveryItemOrderStatus.text = "Order is placed!"
            dropoff.soldOut == true -> holder.deliveryItemOrderStatus.text = "Order is sold out!"
            dropoff.sellingOut == true -> holder.deliveryItemOrderStatus.text = "Order is selling out, hurry!"
        }


    }

    // Gets the number of deliveries in the list
    override fun getItemCount(): Int {
        return deliveries.size
    }

    fun update(newDeliveries: List<Delivery>) {
        val diffCallback = DiffUtilCallback(newDeliveries, deliveries)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)

        deliveries = ArrayList()
        (deliveries as ArrayList<Delivery>).addAll(newDeliveries)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val deliveryItemRestaurantName = view.deliveryItemRestaurantName as TextView
    val deliveryItemRestaurantImage = view.deliveryItemRestaurantImage as ImageView
    val deliveryItemOrderByTime = view.deliveryItemOrderByTime as TextView
    val deliveryItemDeliveryTime = view.deliveryItemDeliveryTime as TextView
    val deliveryItemOrderStatus = view.deliveryItemOrderStatus as TextView
}